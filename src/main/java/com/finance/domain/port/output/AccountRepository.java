package com.finance.domain.port.output;

import com.finance.domain.model.Account;
import com.finance.domain.model.AccountId;
import com.finance.domain.model.UserId;

import java.util.List;
import java.util.Optional;

// domain/port/output/AccountRepository.java
public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(AccountId accountId);
    List<Account> findByUserId(UserId userId);
    Optional<Account> findByIdAndUserId(AccountId accountId, UserId userId);
}
