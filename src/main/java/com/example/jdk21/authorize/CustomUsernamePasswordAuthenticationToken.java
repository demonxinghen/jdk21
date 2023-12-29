package com.example.jdk21.authorize;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author admin
 * @date 2023/12/28 16:27
 * 自定义的AbstractAuthenticationToken,可替代UsernamePasswordAuthenticationToken
 */
public class CustomUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {

    private String username;

    private String password;

    // 未认证时
    public static CustomUsernamePasswordAuthenticationToken unauthenticated(String username, String password) {
        CustomUsernamePasswordAuthenticationToken token = new CustomUsernamePasswordAuthenticationToken(null);
        token.username = username;
        token.password = password;
        token.setAuthenticated(Boolean.FALSE);
        return token;
    }

    // 认证成功后
    public static CustomUsernamePasswordAuthenticationToken authenticated(CustomUserDetails userDetails) {
        CustomUsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken(userDetails.getAuthorities());
        // 认证成功后，将密码清除
        userDetails.setPassword(null);
        // 设置为已认证状态
        authenticationToken.setAuthenticated(Boolean.TRUE);
        // 用户详情为userDetails
        authenticationToken.setDetails(userDetails);
        return authenticationToken;
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public CustomUsernamePasswordAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public String getCredentials() {
        return this.password;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }
}
