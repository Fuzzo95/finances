package com.finance.domain.port.output;

import com.finance.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// domain/port/output/TransactionRepository.java
public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(TransactionId transactionId);
    List<Transaction> findByUserId(UserId userId);
    List<Transaction> findByAccountId(AccountId accountId);
    List<Transaction> findByUserIdAndDateRange(UserId userId, LocalDate startDate, LocalDate endDate);
    Money sumAmountByUserIdAndType(UserId userId, TransactionType type);
}
