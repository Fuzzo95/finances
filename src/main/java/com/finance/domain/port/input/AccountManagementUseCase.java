package com.finance.domain.port.input;

import com.finance.domain.model.*;

import java.util.List;

// domain/port/input/AccountManagementUseCase.java
public interface AccountManagementUseCase {
    Account createAccount(String name, AccountType type, UserId ownerId);
    Account findAccountById(AccountId accountId, UserId userId);
    List<Account> findAccountsByUserId(UserId userId);
    Account updateAccountBalance(AccountId accountId, Money newBalance);
}
