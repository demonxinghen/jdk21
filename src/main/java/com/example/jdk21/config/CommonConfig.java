package com.example.jdk21.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @date 2023/12/27 15:27
 */
@Configuration
public class CommonConfig {

    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setWriterFeatures(
                JSONWriter.Feature.WriteNullListAsEmpty,
                JSONWriter.Feature.PrettyFormat,
                JSONWriter.Feature.WriteMapNullValue,
                JSONWriter.Feature.WriteNullBooleanAsFalse,
                JSONWriter.Feature.MapSortField
        );
        config.setCharset(StandardCharsets.UTF_8);
        return config;
    }

    @Bean
    public SpelExpressionParser spelExpressionParser() {
        return new SpelExpressionParser();
    }
}
