package com.example.jdk21.repository;

import com.example.jdk21.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @date 2023/12/27 15:56
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByUsernameAndDeletedIsFalse(String username);
}
