package com.example.jdk21.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.customizers.GlobalOpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Knife4jConfig {

    @Bean
    open fun orderGlobalOpenApiCustomizer(): GlobalOpenApiCustomizer {
        return GlobalOpenApiCustomizer {
            if (it.tags != null) {
                it.tags.forEach { tag ->
                    val map = mapOf("x-order" to 1)
                    tag.extensions = map
                }
            }
            if (it.paths != null) {
                it.addExtension("x-test", "333")
                it.paths.addExtension("x-abb", 666)
            }
        }
    }

    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
                Info().title("jdk22").version("1.1").description("描述").termsOfService("http://doc.xiaominfo.com").license(
                        License().name("Apache").url("http://doc.xiaominfo.com1")
                )
        )
    }
}