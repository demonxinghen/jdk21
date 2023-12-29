package com.example.jdk21.authorize;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author admin
 * @date 2023/12/28 16:34
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final TokenManager tokenManager;

    public CustomAuthenticationProvider(CustomUserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, TokenManager tokenManager) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken unauthenticatedToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) unauthenticatedToken.getPrincipal();
        String password = (String) unauthenticatedToken.getCredentials();
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        UsernamePasswordAuthenticationToken authenticatedToken = UsernamePasswordAuthenticationToken.authenticated(unauthenticatedToken.getPrincipal(), unauthenticatedToken.getCredentials(), userDetails.getAuthorities());
        String token = tokenManager.generateToken(authenticatedToken);
        userDetails.setToken(token);
        authenticatedToken.setDetails(userDetails);
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
