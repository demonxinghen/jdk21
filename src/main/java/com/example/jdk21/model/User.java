package com.example.jdk21.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 * @date 2023/12/27 16:00
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends BaseModel{

    private String username;

    @Hidden
    private String password;

    @Transient
    private String token;
}
