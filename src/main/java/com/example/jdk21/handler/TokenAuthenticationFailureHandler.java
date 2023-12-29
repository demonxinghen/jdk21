package com.example.jdk21.handler;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @author admin
 * @date 2023/12/28 14:27
 * TokenAuthenticationFilter认证失败之后,会调用该handler<br/>
 * BadCredentialsException异常会到这里处理
 */
@Slf4j
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSON.toJSONString(Result.failure(exception.getMessage())));
    }
}
