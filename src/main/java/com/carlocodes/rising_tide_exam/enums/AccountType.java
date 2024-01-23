package com.carlocodes.rising_tide_exam.enums;

public enum AccountType {
    S("Savings"),
    C("Checking");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
