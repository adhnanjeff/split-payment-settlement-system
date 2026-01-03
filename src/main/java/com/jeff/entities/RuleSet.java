package com.jeff.entities;

import com.jeff.enums.RuleSetMode;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import com.jeff.entities.SplitRule;

@Entity
public class RuleSet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ruleSetId;
    
    private String name;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private RuleSetMode mode;
    
    @OneToMany(mappedBy = "ruleSet", cascade = CascadeType.ALL)
    private List<SplitRule> splitRules = new ArrayList<>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    public RuleSet() {
    }

    public RuleSet(String name, String description, RuleSetMode mode) {
        this.name = name;
        this.description = description;
        this.mode = mode;
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

    public List<SplitRule> getSplitRules() {
        return splitRules;
    }

    public void setSplitRules(List<SplitRule> splitRules) {
        this.splitRules = splitRules;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RuleSet ruleSet = (RuleSet) o;
        return Objects.equals(ruleSetId, ruleSet.ruleSetId) &&
               Objects.equals(name, ruleSet.name) &&
               Objects.equals(description, ruleSet.description) &&
               mode == ruleSet.mode &&
               Objects.equals(createdAt, ruleSet.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleSetId, name, description, mode, createdAt);
    }
}