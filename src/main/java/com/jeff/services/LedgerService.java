package com.jeff.services;

import com.jeff.entities.Account;
import com.jeff.entities.LedgerEntry;
import com.jeff.entities.SplitRule;
import com.jeff.entities.Transaction;
import com.jeff.enums.LedgerEntryType;
import com.jeff.repositories.AccountRepository;
import com.jeff.repositories.LedgerEntryRepository;
import com.jeff.repositories.SplitRuleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class LedgerService {

    private final LedgerEntryRepository ledgerEntryRepo;
    private final SplitRuleRepository splitRuleRepo;
    private final AccountRepository accountRepo;

    public LedgerService(LedgerEntryRepository ledgerEntryRepo, SplitRuleRepository splitRuleRepo, AccountRepository accountRepo) {
        this.ledgerEntryRepo = ledgerEntryRepo;
        this.splitRuleRepo = splitRuleRepo;
        this.accountRepo = accountRepo;
    }

    public void createLedgerEntries(Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        BigDecimal amount = transaction.getAmount();

        // 1. DEBIT entry for payer (money leaving the account)
        LedgerEntry debitEntry = new LedgerEntry(
                LedgerEntryType.DEBIT,
                transaction,
                amount.negate(),
                fromAccount
        );
        transaction.getLedgerEntries().add(debitEntry);

        // 2. Apply split rules if RuleSet is provided
        if (transaction.getRuleSet() != null) {
            applySplitRules(transaction, amount);
        } else {
            // Simple transaction - single CREDIT entry to same account
            LedgerEntry creditEntry = new LedgerEntry(
                    LedgerEntryType.CREDIT,
                    transaction,
                    amount,
                    fromAccount
            );
            transaction.getLedgerEntries().add(creditEntry);
        }
    }
    
    private void applySplitRules(Transaction transaction, BigDecimal totalAmount) {
        List<SplitRule> splitRules = splitRuleRepo.findByRuleSet(transaction.getRuleSet());
        
        for (SplitRule rule : splitRules) {
            Account receiverAccount = accountRepo.findById(rule.getReceiverAccountId())
                    .orElseThrow(() -> new RuntimeException("Receiver account not found: " + rule.getReceiverAccountId()));
            
            BigDecimal splitAmount;
            if (transaction.getRuleSet().getMode().name().equals("PERCENTAGE")) {
                // Calculate percentage of total amount
                splitAmount = totalAmount
                        .multiply(BigDecimal.valueOf(rule.getPercentage()))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            } else {
                // Fixed amount
                splitAmount = rule.getAmount();
            }
            
            // Credit the receiver account balance
            BigDecimal newBalance = receiverAccount.getBalance().add(splitAmount);
            receiverAccount.setBalance(newBalance);
            accountRepo.save(receiverAccount);
            
            // Create CREDIT entry for receiver
            LedgerEntry creditEntry = new LedgerEntry(
                    LedgerEntryType.CREDIT,
                    transaction,
                    splitAmount,
                    receiverAccount
            );
            transaction.getLedgerEntries().add(creditEntry);
        }
    }
}