package com.example.jdk21.controller.user

import com.example.jdk21.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
@Tag(name = "用户模块")
class UserController {

    @GetMapping("/profile")
    @Operation(summary = "简介")
    fun profile() = User("徐辉")
}