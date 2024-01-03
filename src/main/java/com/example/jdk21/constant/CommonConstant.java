package com.example.jdk21.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author admin
 * @date 2023/12/29 19:19
 */
public interface CommonConstant {

    String LOGIN_URL = "/user/login";

    String[] INTERCEPT_IGNORE_URLS = {"/v3/api-docs/**", "/webjars/**", "/doc.html", "/user/login", "/swagger-resources"};

    Set<String> FILTER_IGNORE_URLS = Sets.newHashSet("/v3/api-docs", "/webjars", "/doc.html", "/user/login", "/swagger-resources", "/favicon.ico");
}
