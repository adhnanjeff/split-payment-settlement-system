package com.jeff.entities;

import com.jeff.enums.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import com.jeff.entities.Account;
import com.jeff.entities.LedgerEntry;
import com.jeff.entities.RuleSet;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    
    @ManyToOne
    @JoinColumn(name = "rule_set_id", nullable = true)
    private RuleSet ruleSet;
    
    private BigDecimal amount;
    
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<LedgerEntry> ledgerEntries = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    @Column(unique = true, nullable = false)
    private String idempotencyKey;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public Transaction() {
    }

    public Transaction(Account fromAccount, RuleSet ruleSet, BigDecimal amount, TransactionStatus status, String idempotencyKey) {
        this.fromAccount = fromAccount;
        this.ruleSet = ruleSet;
        this.amount = amount;
        this.status = status;
        this.idempotencyKey = idempotencyKey;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<LedgerEntry> getLedgerEntries() {
        return ledgerEntries;
    }

    public void setLedgerEntries(List<LedgerEntry> ledgerEntries) {
        this.ledgerEntries = ledgerEntries;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && 
               Objects.equals(fromAccount, that.fromAccount) && 
               Objects.equals(ruleSet, that.ruleSet) &&
               Objects.equals(amount, that.amount) && 
               Objects.equals(status, that.status) && 
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(idempotencyKey, that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, fromAccount, ruleSet, amount, status, createdAt, idempotencyKey);
    }
}