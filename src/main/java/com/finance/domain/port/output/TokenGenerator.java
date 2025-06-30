package com.finance.domain.port.output;

// domain/port/output/TokenGenerator.java
public interface TokenGenerator {
    String generateToken(String username);
    String getUsernameFromToken(String token);
    boolean validateToken(String token, String username);
}
