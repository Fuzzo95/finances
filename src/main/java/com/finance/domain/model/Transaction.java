package com.finance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// domain/model/Transaction.java
public record Transaction(TransactionId id, String description, Money amount, TransactionType type, LocalDate date,
                          AccountId accountId, UserId userId, CategoryId categoryId, LocalDateTime createdAt) {
    public Transaction {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (amount == null || amount.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

    }

    public boolean belongsTo(UserId userId) {
        return this.userId.equals(userId);
    }
}

