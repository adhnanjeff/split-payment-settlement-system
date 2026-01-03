package com.jeff.dtos;

import com.jeff.enums.TransactionStatus;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TransactionResponseDto {
    private UUID transactionId;
    private UUID fromAccountId;
    private UUID ruleSetId;
    private BigDecimal amount;
    private TransactionStatus status;
    private Date createdAt;
    private String idempotencyKey;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(UUID transactionId, UUID fromAccountId, UUID ruleSetId,
                                BigDecimal amount, TransactionStatus status, Date createdAt, String idempotencyKey) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.ruleSetId = ruleSetId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.idempotencyKey = idempotencyKey;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(UUID fromAccountId) {
        this.fromAccountId = fromAccountId;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getRuleSetId() {
        return ruleSetId;
    }

    public void setRuleSetId(UUID ruleSetId) {
        this.ruleSetId = ruleSetId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}