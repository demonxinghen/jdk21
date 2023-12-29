package com.example.jdk21.handler;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.pojo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @author admin
 * @date 2023/12/28 17:05
 * 用户未认证的handler
 */
@Slf4j
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        log.error("未获取到用户信息，请登录后重试");
        response.getWriter().write(JSON.toJSONString(Result.failure("未获取到用户信息，请登录后重试")));
    }
}
