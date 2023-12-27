package com.example.jdk21;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author admin
 * @date 2023/12/27 17:39
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder(12).encode("admin"));
    }
}
