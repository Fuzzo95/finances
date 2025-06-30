package com.finance.domain.service;

import com.finance.domain.model.*;
import com.finance.domain.port.input.TransactionManagementUseCase;
import com.finance.domain.port.input.UserManagementUseCase;
import com.finance.domain.port.output.AccountRepository;
import com.finance.domain.port.output.PasswordEncoder;
import com.finance.domain.port.output.TransactionRepository;
import com.finance.domain.port.output.UserRepository;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

// domain/service/UserDomainService.java
@Component
public class UserDomainService implements UserManagementUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDomainService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String email, String password, String firstName, String lastName) {
        Email emailVO = new Email(email);

        if (userRepository.existsByEmail(emailVO)) {
            throw new EmailAlreadyExistsException("Email already registered: " + email);
        }

        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User(null, emailVO, firstName, lastName, encryptedPassword, LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(String email, String password) throws InvalidCredentialsException {
        Email emailVO = new Email(email);
        User user = userRepository.findByEmail(emailVO)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        if (!passwordEncoder.matches(password, user.getEncryptedPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return user;
    }

    @Override
    public User findUserById(UserId userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId.value()));
    }

    @Override
    public User findUserByEmail(String email) {
        Email emailVO = new Email(email);
        return userRepository.findByEmail(emailVO)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }
}

// domain/service/TransactionDomainService.java
@Component
public abstract class TransactionDomainService implements TransactionManagementUseCase {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionDomainService(TransactionRepository transactionRepository,
                                    AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Transaction createTransaction(String description, Money amount, TransactionType type,
                                         LocalDate date, AccountId accountId, UserId userId, CategoryId categoryId) throws Exception {
        // Verificar que la cuenta pertenece al usuario
        Account account = accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found or not owned by user"));

        // Crear la transacción
        Transaction transaction = new Transaction(
                null, description, amount, type, date, accountId, userId, categoryId, LocalDateTime.now()
        );

        // Actualizar el balance de la cuenta
        account.addTransaction(amount, type);
        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findTransactionsByUserId(UserId userId) {
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public FinancialSummary getFinancialSummary(UserId userId) {
        Money totalIncome = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.INCOME);
        Money totalExpenses = transactionRepository.sumAmountByUserIdAndType(userId, TransactionType.EXPENSE);

        if (totalIncome == null) totalIncome = new Money(BigDecimal.ZERO, Currency.getInstance("USD"));
        if (totalExpenses == null) totalExpenses = new Money(BigDecimal.ZERO, Currency.getInstance("USD"));

        return new FinancialSummary(totalIncome, totalExpenses);
    }
}
