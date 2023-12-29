package com.example.jdk21.authorize;

import org.springframework.security.core.Authentication;

/**
 * @author admin
 * @date 2023/12/28 13:51
 */
public interface TokenManager {

    String generateToken(Authentication authentication);

    void removeToken(String token);

    Authentication getAuthentication(String token);

    void delayExpired(String token);
}
