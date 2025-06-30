package com.finance.domain.model;

public enum AccountType {
    CHECKING(true),
    SAVINGS(false),
    CREDIT_CARD(true),
    INVESTMENT(false);

    private final boolean allowsNegativeBalance;

    AccountType(boolean allowsNegativeBalance) {
        this.allowsNegativeBalance = allowsNegativeBalance;
    }

    public boolean allowsNegativeBalance() {
        return allowsNegativeBalance;
    }
}
