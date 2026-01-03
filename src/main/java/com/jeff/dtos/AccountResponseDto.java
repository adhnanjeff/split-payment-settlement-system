package com.jeff.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class AccountResponseDto {
    private UUID accountId;
    private String holderName;
    private String accType;
    private BigDecimal balance;
    private Date createdAt;

    public AccountResponseDto() {
    }

    public AccountResponseDto(UUID accountId, String holderName, String accType, BigDecimal balance, Date createdAt) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.accType = accType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}