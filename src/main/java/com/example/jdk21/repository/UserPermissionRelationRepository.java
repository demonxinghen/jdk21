package com.example.jdk21.repository;

import com.example.jdk21.model.UserPermissionRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2023/12/27 15:56
 */
@Repository
public interface UserPermissionRelationRepository extends BaseRepository<UserPermissionRelation> {

    List<UserPermissionRelation> findAllByUserId(String id);
}
