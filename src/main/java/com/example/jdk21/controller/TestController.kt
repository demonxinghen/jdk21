package com.example.jdk21.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "测试模块")
@RestController
@RequestMapping("/test")
open class TestController {

    @GetMapping
    @Operation(summary = "我就测试下")
    fun test() = "123"
}