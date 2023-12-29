package com.example.jdk21.authorize;

import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @author admin
 * @date 2023/12/28 14:42
 */
@Component
@Slf4j
public class DefaultTokenManager implements TokenManager {

    private static final Cache<String, Authentication> TOKEN_MANAGER = Caffeine.newBuilder().expireAfterAccess(Duration.ofMinutes(15)).expireAfterWrite(Duration.ofMinutes(15)).removalListener((key, value, cause) -> log.info("key: " + key + " value: " + value + " cause: " + cause)).build();

    @Override
    public String generateToken(Authentication authentication) {
        String token = UUID.randomUUID().toString();
        TOKEN_MANAGER.put(token, authentication);
        return token;
    }

    @Override
    public void removeToken(String token) {
        TOKEN_MANAGER.invalidate(token);
    }

    @Override
    public Authentication getAuthentication(String token) {
        Authentication authentication = TOKEN_MANAGER.getIfPresent(token);
        log.info("authentication: " + JSON.toJSONString(authentication));
        return authentication;
    }

    @Override
    public void delayExpired(String token) {
        // 使用Caffeine会自动续期,如果使用redis,则要续期
    }
}
