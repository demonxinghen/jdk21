package com.example.jdk21.handler;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.authorize.TokenManager;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author admin
 * @date 2023/12/28 14:27
 * TokenAuthenticationFilter认证成功之后,会调用该handler
 */
@Slf4j
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private TokenManager tokenManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 返回访问token
        String token = tokenManager.generateToken(authentication);
        // 返回refreshToken
        String refreshToken = tokenManager.generateToken(authentication);
        log.info("登录成功，登录人：" + JSON.toJSONString(userDetails));
    }
}
