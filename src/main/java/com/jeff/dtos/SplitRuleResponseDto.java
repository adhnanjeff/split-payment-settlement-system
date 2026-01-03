package com.jeff.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class SplitRuleResponseDto {
    private UUID ruleId;
    private UUID ruleSetId;
    private UUID receiverAccountId;
    private Integer percentage;
    private BigDecimal amount;
    private Date createdAt;

    public SplitRuleResponseDto() {
    }

    public SplitRuleResponseDto(UUID ruleId, UUID ruleSetId, UUID receiverAccountId, Integer percentage, BigDecimal amount, Date createdAt) {
        this.ruleId = ruleId;
        this.ruleSetId = ruleSetId;
        this.receiverAccountId = receiverAccountId;
        this.percentage = percentage;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public UUID getRuleId() {
        return ruleId;
    }

    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}