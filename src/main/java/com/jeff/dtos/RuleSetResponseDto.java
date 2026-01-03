package com.jeff.dtos;

import com.jeff.enums.RuleSetMode;

import java.util.Date;
import java.util.UUID;

public class RuleSetResponseDto {
    private UUID ruleSetId;
    private String name;
    private String description;
    private RuleSetMode mode;
    private Date createdAt;

    public RuleSetResponseDto() {
    }

    public RuleSetResponseDto(UUID ruleSetId, String name, String description, RuleSetMode mode, Date createdAt) {
        this.ruleSetId = ruleSetId;
        this.name = name;
        this.description = description;
        this.mode = mode;
        this.createdAt = createdAt;
    }

    public UUID getRuleSetId() {
        return ruleSetId;
    }

    public void setRuleSetId(UUID ruleSetId) {
        this.ruleSetId = ruleSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleSetMode getMode() {
        return mode;
    }

    public void setMode(RuleSetMode mode) {
        this.mode = mode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}