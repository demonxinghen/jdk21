package com.example.jdk21.annotation;

import java.lang.annotation.*;

/**
 * @author zimbean
 * @version 1.0
 * @date 2021/9/11 12:34
 */
// 该注解作用于方法
@Target(ElementType.METHOD)
// 该注解在代码运行时也是存在的
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {

    String[] value() default {};

    String[] authorities() default {};

    String[] roles() default {};
}