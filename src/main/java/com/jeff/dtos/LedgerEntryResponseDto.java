package com.jeff.dtos;

import com.jeff.enums.LedgerEntryType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class LedgerEntryResponseDto {
    private UUID entryId;
    private LedgerEntryType entryType;
    private UUID transactionId;
    private UUID accountId;
    private String accountHolderName;
    private BigDecimal amount;
    private Date createdAt;

    public LedgerEntryResponseDto() {
    }

    public LedgerEntryResponseDto(UUID entryId, LedgerEntryType entryType, UUID transactionId, 
                                UUID accountId, String accountHolderName, BigDecimal amount, Date createdAt) {
        this.entryId = entryId;
        this.entryType = entryType;
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public UUID getEntryId() {
        return entryId;
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId;
    }

    public LedgerEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(LedgerEntryType entryType) {
        this.entryType = entryType;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}