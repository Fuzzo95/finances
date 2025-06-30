package com.finance.domain.model;

public record CategoryId(Long value) {
    public CategoryId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("CategoryId must be positive");
        }
    }
}
