package com.example.jdk21.authorize;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
        CustomUsernamePasswordAuthenticationToken authenticationToken = (CustomUsernamePasswordAuthenticationToken) authentication;
        String username = authenticationToken.getPrincipal();
        String password = authenticationToken.getCredentials();
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        CustomUsernamePasswordAuthenticationToken authenticationToken1 = CustomUsernamePasswordAuthenticationToken.customUsernamePasswordAuthenticationToken(userDetails);
        String token = tokenManager.generateToken(authenticationToken1);
        ((CustomUserDetails) authenticationToken1.getDetails()).setToken(token);
        return authenticationToken1;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
