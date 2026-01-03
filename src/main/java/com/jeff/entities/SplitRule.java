package com.jeff.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import com.jeff.entities.RuleSet;

@Entity
public class SplitRule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ruleId;
    
    @ManyToOne
    @JoinColumn(name = "rule_set_id")
    private RuleSet ruleSet;
    
    private UUID receiverAccountId;
    private Integer percentage;
    private BigDecimal amount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public SplitRule() {
    }

    public SplitRule(RuleSet ruleSet, UUID receiverAccountId, Integer percentage, BigDecimal amount) {
        this.ruleSet = ruleSet;
        this.receiverAccountId = receiverAccountId;
        this.percentage = percentage;
        this.amount = amount;
    }

    public UUID getRuleId() {
        return ruleId;
    }

    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SplitRule splitRule = (SplitRule) o;
        return Objects.equals(ruleId, splitRule.ruleId) &&
               Objects.equals(ruleSet, splitRule.ruleSet) &&
               Objects.equals(receiverAccountId, splitRule.receiverAccountId) &&
               Objects.equals(percentage, splitRule.percentage) &&
               Objects.equals(amount, splitRule.amount) &&
               Objects.equals(createdAt, splitRule.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, ruleSet, receiverAccountId, percentage, amount, createdAt);
    }
}