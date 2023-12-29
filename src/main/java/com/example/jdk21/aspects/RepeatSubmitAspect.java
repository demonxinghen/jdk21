package com.example.jdk21.aspects;

import com.example.jdk21.annotation.RepeatSubmit;
import com.example.jdk21.exception.BizException;
import com.example.jdk21.utils.UserUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2023/12/22 15:20
 * 重复提交校验
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(repeatSubmit)")
    public void pointCut(RepeatSubmit repeatSubmit) {
    }

    @Around(value = "pointCut(repeatSubmit)", argNames = "joinPoint,repeatSubmit")
    public Object around(ProceedingJoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        String path = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getServletPath();
        StringBuilder sb = new StringBuilder();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        sb.append(path).append(":").append(method.getName()).append(":").append(Arrays.toString(joinPoint.getArgs()));
        if (UserUtil.get() != null) {
            //sb.append(":").append(UserUtil.get().getToken());
        }
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(sb.toString(), "", repeatSubmit.lockTime(), TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(absent)) {
            throw new BizException("请勿重复提交");
        }
        return joinPoint.proceed();
    }
}
