package com.finance.domain.port.output;

import com.finance.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// domain/port/output/UserRepository.java
public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId userId);
    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);
}

