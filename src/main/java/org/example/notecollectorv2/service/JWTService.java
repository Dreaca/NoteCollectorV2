package org.example.notecollectorv2.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(UserDetails user);
    boolean validateToken(String token, UserDetails user);
    String refreshToken(UserDetails user);
}
