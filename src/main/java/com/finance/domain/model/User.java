package com.finance.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public record User (UserId id, Email email, String firstName, String lastName,
                    String encryptedPassword, LocalDateTime createdAt) {
}

