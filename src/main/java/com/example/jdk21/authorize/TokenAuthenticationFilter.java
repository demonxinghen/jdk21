package com.example.jdk21.authorize;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.pojo.UserLoginPojo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2023/12/28 14:16
 * 默认的是表单登录UsernamePasswordAuthenticationFilter,不支持从RequestBody里获取,所以此处自定义一个
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TokenAuthenticationFilter() {
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    public TokenAuthenticationFilter(AntPathRequestMatcher matcher) {
        super(matcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        String bodyStr = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        UserLoginPojo userLoginPojo = JSON.parseObject(bodyStr, UserLoginPojo.class);
        if (userLoginPojo == null){
            throw new BadCredentialsException("请输入用户名、密码。");
        }
        if (StringUtils.isBlank(userLoginPojo.getUsername())) {
            throw new BadCredentialsException("用户名不能为空");
        }
        if (StringUtils.isBlank(userLoginPojo.getPassword())) {
            throw new BadCredentialsException("密码不能为空");
        }
        CustomUsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = CustomUsernamePasswordAuthenticationToken.customUsernamePasswordUnAuthenticationToken(userLoginPojo.getUsername(), userLoginPojo.getPassword());
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
