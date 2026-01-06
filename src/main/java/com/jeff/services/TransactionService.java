package com.jeff.services;

import com.jeff.dtos.TransactionRequestDto;
import com.jeff.dtos.TransactionResponseDto;
import com.jeff.entities.Account;
import com.jeff.entities.RuleSet;
import com.jeff.entities.Transaction;
import com.jeff.enums.TransactionStatus;
import com.jeff.repositories.AccountRepository;
import com.jeff.repositories.RuleSetRepository;
import com.jeff.repositories.TransactionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.jeff.services.LedgerService;
import com.jeff.services.RuleSetService;
import com.jeff.services.TransactionService;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;
    private final RuleSetRepository ruleSetRepo;
    private final LedgerService ledgerService;

    public TransactionService(TransactionRepository transactionRepo, AccountRepository accountRepo, RuleSetRepository ruleSetRepo, LedgerService ledgerService) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
        this.ruleSetRepo = ruleSetRepo;
        this.ledgerService = ledgerService;
    }

    public List<TransactionResponseDto> findAllTransactions() {
        return transactionRepo.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public TransactionResponseDto findTransactionById(UUID id) {
        Transaction transaction = transactionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction with id " + id + " not found"));
        return toResponseDto(transaction);
    }

    public void deleteTransactionById(UUID id) {
        transactionRepo.deleteById(id);
    }

    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {
        try {
            // 1. Validate inputs
            validateTransactionRequest(requestDto);
            
            // 2. Check for existing transaction (idempotency)
            Optional<Transaction> existingTransaction = transactionRepo.findByIdempotencyKey(requestDto.getIdempotencyKey());
            if (existingTransaction.isPresent()) {
                return toResponseDto(existingTransaction.get());
            }
            
            // 3. Fetch and validate entities
            Account fromAccount = accountRepo.findById(requestDto.getFromAccountId())
                    .orElseThrow(() -> new RuntimeException("From account not found"));
            
            // RuleSet is optional - can be null for simple transactions
            RuleSet ruleSet = null;
            if (requestDto.getRuleSetId() != null) {
                ruleSet = ruleSetRepo.findById(requestDto.getRuleSetId())
                        .orElseThrow(() -> new RuntimeException("RuleSet not found"));
            }
            
            // 4. Business validations
            validateBusinessRules(fromAccount, requestDto.getAmount());
            
            // 5. Create transaction (PENDING status)
            Transaction transaction = toEntity(requestDto, fromAccount, ruleSet);
            transaction.setStatus(TransactionStatus.PENDING);
            
            // 6. Process payment (deduct balance)
            processPayment(fromAccount, requestDto.getAmount());
            
            // 7. Create ledger entries (only after successful payment)
            ledgerService.createLedgerEntries(transaction);
            
            // 8. Mark transaction as COMPLETED
            transaction.setStatus(TransactionStatus.COMPLETED);
            
            // 9. Save everything (atomic)
            Transaction savedTransaction = transactionRepo.save(transaction);
            accountRepo.save(fromAccount);
            
            return toResponseDto(savedTransaction);
            
        } catch (DataIntegrityViolationException e) {
            // Handle duplicate idempotency key from concurrent requests
            Transaction existingTransaction = transactionRepo.findByIdempotencyKey(requestDto.getIdempotencyKey())
                    .orElseThrow(() -> new RuntimeException("Unexpected error: transaction not found after constraint violation"));
            return toResponseDto(existingTransaction);
        } catch (Exception e) {
            // Any failure rolls back entire transaction (ACID)
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        }
    }
    
    private void validateTransactionRequest(TransactionRequestDto requestDto) {
        if (requestDto.getAmount() == null || requestDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        if (requestDto.getFromAccountId() == null) {
            throw new RuntimeException("From account ID is required");
        }
        if (requestDto.getIdempotencyKey() == null || requestDto.getIdempotencyKey().trim().isEmpty()) {
            throw new RuntimeException("Idempotency key is required");
        }
    }
    
    private void validateBusinessRules(Account fromAccount, BigDecimal amount) {
        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance. Available: " + fromAccount.getBalance() + ", Required: " + amount);
        }
        
        // Check account status (example business rule)
        if ("FROZEN".equals(fromAccount.getAccType())) {
            throw new RuntimeException("Account is frozen");
        }
    }
    
    private void processPayment(Account fromAccount, BigDecimal amount) {
        // Deduct amount from payer's account
        BigDecimal newBalance = fromAccount.getBalance().subtract(amount);
        fromAccount.setBalance(newBalance);
    }

    public TransactionResponseDto updateTransaction(UUID id, TransactionRequestDto requestDto) {
        Optional<Transaction> optionalTransaction = transactionRepo.findById(id);

        if (optionalTransaction.isPresent()) {
            Transaction existingTransaction = optionalTransaction.get();
            
            Account fromAccount = accountRepo.findById(requestDto.getFromAccountId())
                    .orElseThrow(() -> new RuntimeException("From account not found"));
            
            RuleSet ruleSet = null;
            if (requestDto.getRuleSetId() != null) {
                ruleSet = ruleSetRepo.findById(requestDto.getRuleSetId())
                        .orElseThrow(() -> new RuntimeException("RuleSet not found"));
            }

            existingTransaction.setFromAccount(fromAccount);
            existingTransaction.setRuleSet(ruleSet);
            existingTransaction.setAmount(requestDto.getAmount());
            existingTransaction.setIdempotencyKey(requestDto.getIdempotencyKey());

            Transaction savedTransaction = transactionRepo.save(existingTransaction);
            return toResponseDto(savedTransaction);
        } else {
            throw new RuntimeException("Transaction with id " + id + " not found");
        }
    }

    private TransactionResponseDto toResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getTransactionId(),
                transaction.getFromAccount().getAccountId(),
                transaction.getRuleSet() != null ? transaction.getRuleSet().getRuleSetId() : null,
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getIdempotencyKey()
        );
    }

    private Transaction toEntity(TransactionRequestDto requestDto, Account fromAccount, RuleSet ruleSet) {
        return new Transaction(
                fromAccount,
                ruleSet,
                requestDto.getAmount(),
                TransactionStatus.PENDING,
                requestDto.getIdempotencyKey()
        );
    }
}