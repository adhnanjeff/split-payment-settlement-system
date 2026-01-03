package com.jeff.dtos;

import java.math.BigDecimal;

public class AccountRequestDto {
    private String holderName;
    private String accType;
    private BigDecimal balance;

    public AccountRequestDto() {
    }

    public AccountRequestDto(String holderName, String accType, BigDecimal balance) {
        this.holderName = holderName;
        this.accType = accType;
        this.balance = balance;
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
}