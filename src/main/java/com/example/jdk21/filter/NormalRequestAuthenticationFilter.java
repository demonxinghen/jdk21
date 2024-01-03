package com.example.jdk21.filter;

import com.example.jdk21.constant.CommonConstant;
import com.example.jdk21.utils.CommonUtil;
import com.example.jdk21.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author admin
 * @date 2023/12/29 16:04
 * 解析普通请求的token
 */
@Slf4j
public class NormalRequestAuthenticationFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;

    public NormalRequestAuthenticationFilter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return CommonUtil.isIgnoreUrl(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new InsufficientAuthenticationException("Invalid token");
        }
        SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = strategy.createEmptyContext();
        log.info("进入请求：{}", request.getRequestURI());
        Authentication authentication = redisUtil.get(token, Authentication.class);
        context.setAuthentication(authentication);
        strategy.setContext(context);
        filterChain.doFilter(request, response);
    }

    /**
     * 在继承GenericFilterBean的情况下，需要标记FILTER_APPLIED,否则过滤器会执行两次<br>
     * 原因是spring容器托管的GenericFilterBean的bean,会自动加入到servlet的filter chain,鉴权通过后又在spring security的filter chain中<br>
     * 本例中继承OncePerRequestFilter也可以实现这个效果
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("进入请求：{}", request.getRequestURI());
        if (Boolean.TRUE.equals(request.getAttribute("FILTER_APPLIED")) || CommonConstant.LOGIN_URL.equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        request.setAttribute("FILTER_APPLIED", true);
        String token = request.getHeader("token");
        SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = strategy.createEmptyContext();
        Authentication authentication = redisUtil.get(token, Authentication.class);
        context.setAuthentication(authentication);
        strategy.setContext(context);
        chain.doFilter(request, response);
        request.removeAttribute("FILTER_APPLIED");
    }
}
