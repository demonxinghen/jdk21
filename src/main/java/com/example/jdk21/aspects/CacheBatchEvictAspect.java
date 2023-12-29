package com.example.jdk21.aspects;

import com.example.jdk21.annotation.CacheBatchEvict;
import com.example.jdk21.exception.BizException;
import com.example.jdk21.model.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 * @date 2023/12/21 9:47
 */
@Aspect
@Slf4j
@Component
public class CacheBatchEvictAspect {

    @Resource
    private SpelExpressionParser parser;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Pointcut("@annotation(com.example.jdk21.annotation.CacheBatchEvict)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CacheBatchEvict annotation = methodSignature.getMethod().getAnnotation(CacheBatchEvict.class);
        String[] cacheNames = annotation.cacheNames();
        String spel = annotation.key();
        if (StringUtils.isBlank(spel)) {
            log.error("key is null");
            return null;
        }
        Expression expression = parser.parseExpression(spel);
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        if (parameterNames == null) {
            throw new BizException("解析CacheBatchEvict缓存参数失败");
        }
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        Object value = expression.getValue(context);
        if (value instanceof List) {
            if (CollectionUtils.isEmpty((List<?>) value)) {
                log.error("获取参数失败：{}", spel);
                return null;
            }
            for (String cacheName : cacheNames) {
                List<?> list = (List<?>) value;
                for (Object key : list) {
                    redisTemplate.delete(cacheName + key);
                }
            }
        }
        return joinPoint.proceed();
    }
}
