package com.jeff.dtos;

import com.jeff.enums.RuleSetMode;

public class RuleSetRequestDto {
    private String name;
    private String description;
    private RuleSetMode mode;

    public RuleSetRequestDto() {
    }

    public RuleSetRequestDto(String name, String description, RuleSetMode mode) {
        this.name = name;
        this.description = description;
        this.mode = mode;
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
}