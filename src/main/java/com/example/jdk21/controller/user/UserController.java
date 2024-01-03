package com.example.jdk21.controller.user;

import com.example.jdk21.model.User;
import com.example.jdk21.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author admin
 * @date 2023/12/27 17:26
 */
@RequestMapping("/user")
@RestController
@Tag(name = "用户模块")
public class UserController {

    @Resource
    private UserService userService;

    @PreAuthorize("hasAuthority('1')")
    @GetMapping("/{id}")
    @Operation(summary = "详情")
    public User detail(@PathVariable("id") String id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }
}
