package com.jeff.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class SplitRuleRequestDto {
    private UUID ruleSetId;
    private UUID receiverAccountId;
    private Integer percentage;
    private BigDecimal amount;

    public SplitRuleRequestDto() {
    }

    public SplitRuleRequestDto(UUID ruleSetId, UUID receiverAccountId, Integer percentage, BigDecimal amount) {
        this.ruleSetId = ruleSetId;
        this.receiverAccountId = receiverAccountId;
        this.percentage = percentage;
        this.amount = amount;
    }

    public UUID getRuleSetId() {
        return ruleSetId;
    }

    public void setRuleSetId(UUID ruleSetId) {
        this.ruleSetId = ruleSetId;
    }

    public UUID getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(UUID receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}