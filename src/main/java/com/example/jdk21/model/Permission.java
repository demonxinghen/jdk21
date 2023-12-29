package com.example.jdk21.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 * @date 2023/12/28 10:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Permission extends BaseModel{

    private String name;

    private String parent;
}
