package com.example.jdk21.model;

import com.example.jdk21.config.SnowflakeIDGenerator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLRestriction;

/**
 * @author admin
 * @date 2023/12/27 15:59
 */
@SQLRestriction(value = "deleted = false")
@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(generator = "SnowflakeIDGenerator")
    @GenericGenerator(name = "SnowflakeIDGenerator", type = SnowflakeIDGenerator.class)
    private String id;

    private String creator;

    @Transient
    private String creatorName;

    private String createTime;

    private String modifier;

    @Transient
    private String modifierName;

    private String modifyTime;

    private boolean deleted;
}
