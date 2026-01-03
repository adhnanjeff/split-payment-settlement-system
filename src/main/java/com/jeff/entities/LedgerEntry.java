package com.jeff.entities;

import com.jeff.enums.LedgerEntryType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import com.jeff.entities.Account;
import com.jeff.entities.LedgerEntry;
import com.jeff.entities.Transaction;

@Entity
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID entryId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private LedgerEntryType entryType;

    @ManyToOne
    @JoinColumn(name= "transaction_id")
    private Transaction transaction;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public LedgerEntry() {
    }

    public LedgerEntry(LedgerEntryType entryType, Transaction transaction, BigDecimal amount, Account account) {
        this.entryType = entryType;
        this.transaction = transaction;
        this.amount = amount;
        this.account = account;
    }

    public UUID getEntryId() {
        return entryId;
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public LedgerEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(LedgerEntryType entryType) {
        this.entryType = entryType;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LedgerEntry that = (LedgerEntry) o;
        return Objects.equals(entryId, that.entryId) &&
               Objects.equals(createdAt, that.createdAt) &&
               Objects.equals(entryType, that.entryType) &&
               Objects.equals(transaction, that.transaction) &&
               Objects.equals(amount, that.amount) &&
               Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId, createdAt, entryType, transaction, amount, account);
    }
}
