package com.example.jdk21.config;

import com.example.jdk21.authorize.*;
import com.example.jdk21.handler.*;
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
                        authorize.requestMatchers("/v3/api-docs/**", "/webjars/**", "/doc.html", "/user/login").permitAll()
                                .anyRequest().authenticated()
                );
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);

        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.with(new UsernamePasswordLoginConfig<>(authenticationSuccessHandler(), authenticationFailureHandler()), Customizer.withDefaults());

        httpSecurity.logout((logout) -> logout.logoutUrl("/user/logout").logoutSuccessHandler(logoutSuccessHandler()));

        httpSecurity.exceptionHandling( e -> e.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler()));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder(), tokenManager);
    }

    @Bean
    public TokenAccessDeniedHandler accessDeniedHandler(){
        return new TokenAccessDeniedHandler();
    }

    @Bean
    public TokenAuthenticationEntryPoint authenticationEntryPoint(){
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public TokenAuthenticationSuccessHandler authenticationSuccessHandler(){
        return new TokenAuthenticationSuccessHandler();
    }

    @Bean
    public TokenAuthenticationFailureHandler authenticationFailureHandler(){
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * 替代WebSecurityConfigurerAdapter#configure(WebSecurity web)方法
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1");
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
