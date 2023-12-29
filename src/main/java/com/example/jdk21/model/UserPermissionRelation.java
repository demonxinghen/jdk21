package com.example.jdk21.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author admin
 * @date 2023/12/28 10:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserPermissionRelation extends BaseModel{

    private String userId;

    private String permissionId;
}
