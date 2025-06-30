package com.finance.domain.port.input;

import com.finance.domain.model.*;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.List;

// domain/port/input/UserManagementUseCase.java
public interface UserManagementUseCase {
    User registerUser(String email, String password, String firstName, String lastName);
    User authenticateUser(String email, String password) throws InvalidCredentialsException;
    User findUserById(UserId userId);
    User findUserByEmail(String email);
}

// domain/port/input/TransactionManagementUseCase.java
public interface TransactionManagementUseCase {
    Transaction createTransaction(String description, Money amount, TransactionType type,
                                  LocalDate date, AccountId accountId, UserId userId, CategoryId categoryId) throws Exception;
    List<Transaction> findTransactionsByUserId(UserId userId);
    List<Transaction> findTransactionsByAccountId(AccountId accountId, UserId userId);
    List<Transaction> findTransactionsByDateRange(UserId userId, LocalDate startDate, LocalDate endDate);
    FinancialSummary getFinancialSummary(UserId userId);
}
