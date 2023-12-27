package com.example.jdk21.model;

import jakarta.persistence.Entity;
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

    private String password;
}
