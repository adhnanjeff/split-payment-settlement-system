package com.jeff.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionRequestDto {
    private UUID fromAccountId;
    private UUID ruleSetId;
    private BigDecimal amount;
    private String idempotencyKey;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(UUID fromAccountId, UUID ruleSetId, BigDecimal amount, String idempotencyKey) {
        this.fromAccountId = fromAccountId;
        this.ruleSetId = ruleSetId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
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