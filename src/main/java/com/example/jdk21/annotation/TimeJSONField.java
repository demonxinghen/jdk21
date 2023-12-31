package com.example.jdk21.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TimeJSONField{

    String pattern() default "";

    boolean separate() default true;

    String suffix() default "";
}
