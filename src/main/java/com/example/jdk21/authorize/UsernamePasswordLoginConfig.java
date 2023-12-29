package com.example.jdk21.authorize;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author admin
 * @date 2023/12/28 16:59
 */
public class UsernamePasswordLoginConfig<H extends HttpSecurityBuilder<H>> extends AbstractAuthenticationFilterConfigurer<H, UsernamePasswordLoginConfig<H>, TokenAuthenticationFilter> {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    public UsernamePasswordLoginConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(new TokenAuthenticationFilter(), "/user/login");
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    /**
     * 给filter配置各种回调
     * @param http
     */
    @Override
    public void configure(H http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        TokenAuthenticationFilter authenticationFilter = this.getAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        http.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void init(H http) throws Exception {
        // 父类里updateAuthenticationDefaults配置登录处理地址，失败跳转地址，注销成功跳转地址。
        // updateAccessDefaults主要是对 loginPage、loginProcessingUrl、failureUrl 进行 permitAll 设置（如果用户配置了 permitAll 的话）。
        // registerDefaultAuthenticationEntryPoint注册异常的处理器。
        super.init(http);
    }
}
