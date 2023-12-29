package com.example.jdk21.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author admin
 */
@Aspect
@Slf4j
@Component
public class RepositoryAspect {

    @Pointcut(value = "execution(public * com.example.jdk21.repository.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(point.getSignature() + " sql执行开始");
        Object result = point.proceed();
        stopWatch.stop();
        if (stopWatch.getTotalTimeSeconds() > 3) {
            log.error("查询耗时" + stopWatch.getTotalTimeSeconds() + "秒,请检查sql语句");
        }
        return result;
    }
}
