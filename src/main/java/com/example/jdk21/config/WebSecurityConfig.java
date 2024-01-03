package com.example.jdk21.config;

import com.example.jdk21.authorize.CustomAuthenticationProvider;
import com.example.jdk21.authorize.CustomUserDetailsServiceImpl;
import com.example.jdk21.authorize.TokenManager;
import com.example.jdk21.authorize.UsernamePasswordLoginConfig;
import com.example.jdk21.filter.NormalRequestAuthenticationFilter;
import com.example.jdk21.handler.*;
import com.example.jdk21.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

/**
 * @author admin
 * @date 2023/12/27 14:33
 */
@Configuration
public class WebSecurityConfig {

    @Resource
    private CustomUserDetailsServiceImpl userDetailsService;

    @Resource
    private TokenManager tokenManager;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 替代WebSecurityConfigurerAdapter#configure(HttpSecurity http)方法
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        //authorize.requestMatchers(CommonConstant.INTERCEPT_IGNORE_URLS).permitAll()
                        //        .anyRequest().authenticated()
                        authorize.anyRequest().permitAll()
                );
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        //httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.with(new UsernamePasswordLoginConfig<>(authenticationSuccessHandler(), authenticationFailureHandler()), Customizer.withDefaults());
        httpSecurity.addFilterAfter(normalRequestAuthenticationFilter(), SecurityContextHolderFilter.class);

        httpSecurity.logout((logout) -> logout.logoutUrl("/user/logout").logoutSuccessHandler(logoutSuccessHandler()));

        httpSecurity.exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler()));
        return httpSecurity.build();
    }

    @Bean
    public NormalRequestAuthenticationFilter normalRequestAuthenticationFilter() {
        return new NormalRequestAuthenticationFilter(redisUtil);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder(), tokenManager);
    }

    @Bean
    public TokenAccessDeniedHandler accessDeniedHandler() {
        return new TokenAccessDeniedHandler();
    }

    @Bean
    public TokenAuthenticationEntryPoint authenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public TokenLogoutSuccessHandler logoutSuccessHandler() {
        return new TokenLogoutSuccessHandler();
    }

    @Bean
    public TokenAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new TokenAuthenticationSuccessHandler();
    }

    @Bean
    public TokenAuthenticationFailureHandler authenticationFailureHandler() {
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * 替代WebSecurityConfigurerAdapter#configure(WebSecurity web)方法
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicon.ico");
    }

    /**
     * 密码加密方式
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
