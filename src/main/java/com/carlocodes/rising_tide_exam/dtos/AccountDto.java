package com.carlocodes.rising_tide_exam.dtos;

import com.carlocodes.rising_tide_exam.enums.AccountType;

import java.math.BigDecimal;

public class AccountDto {
    private Long accountNumber;
    private AccountType accountType;
    private BigDecimal availableBalance;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "accountNumber=" + accountNumber +
                ", accountType=" + accountType +
                ", availableBalance=" + availableBalance +
                '}';
    }
}
