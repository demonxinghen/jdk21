package com.example.jdk21.service;

import com.example.jdk21.model.User;

public interface UserService {
    User getById(String id);

    void deleteById(String id);
}
