package com.example.jdk21.service;

import com.example.jdk21.model.User;
import com.example.jdk21.pojo.UserLoginPojo;

public interface UserService {
    User getById(String id);

    void deleteById(String id);

    User login(UserLoginPojo pojo);
}
