package com.finance.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;

// domain/model/Account.java
@Getter
public class Account {
    // Getters
    private final AccountId id;
    private final String name;
    private final AccountType type;
    private Money balance;
    private final UserId ownerId;
    private final LocalDateTime createdAt;

    public Account(AccountId id, String name, AccountType type,
                   Money balance, UserId ownerId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public void addTransaction(Money amount, TransactionType transactionType) throws Exception {
        switch (transactionType) {
            case INCOME:
                this.balance = this.balance.add(amount);
                break;
            case EXPENSE:
                if (this.balance.isLessThan(amount) && !type.allowsNegativeBalance()) {
                    throw new Exception("Insufficient funds");
                }
                this.balance = this.balance.subtract(amount);
                break;
        }
    }

    public boolean belongsTo(UserId userId) {
        return this.ownerId.equals(userId);
    }

}

