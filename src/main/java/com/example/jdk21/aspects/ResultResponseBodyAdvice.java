package com.example.jdk21.aspects;

import com.alibaba.fastjson2.JSON;
import com.example.jdk21.pojo.Result;
import com.example.jdk21.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author admin
 */
@RestControllerAdvice
@Slf4j
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class selectedConverterType, ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if (CommonUtil.isIgnoreUrl(request.getURI().getPath())) {
            return body;
        }
        if (!(body instanceof Result)) {
            if (body instanceof String) {
                return JSON.toJSONString(Result.success(body));
            }
            return Result.success(body);
        }
        return body;
    }
}
