package com.example.jdk21.aspects;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.utils.IPUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2023/11/22 11:01
 */
@Aspect
@Slf4j
@Component
public class ControllerAspect {

    @Pointcut(value = "execution(public * com.example.jdk21.controller.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return point.proceed();
        }
        HttpServletRequest request = attributes.getRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(point.getSignature() + " 请求开始");
        log.info("IP:{},请求开始,url:{}", IPUtil.getRealIp(), request.getRequestURI());
        Object result = point.proceed();
        stopWatch.stop();
        log.info("IP:{},请求结束,url:{},请求方式:{},方法:{}.{},参数:{},耗时{}秒", IPUtil.getRealIp(), request.getRequestURI(), request.getMethod(), point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), JSON.toJSONString(getRequestParams(point)), stopWatch.getTotalTimeSeconds());
        return result;
    }

    private Map<String, Object> getRequestParams(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> requestParams = new HashMap<>();

        //参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            if (value instanceof ServletRequest){
                continue;
            }

            //如果是文件对象
            if (value instanceof MultipartFile file) {
                value = file.getOriginalFilename();  //获取文件名
            }

            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }
}
