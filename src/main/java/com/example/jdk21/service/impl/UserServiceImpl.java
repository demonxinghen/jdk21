package com.example.jdk21.service.impl;

import com.example.jdk21.model.User;
import com.example.jdk21.repository.UserRepository;
import com.example.jdk21.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @date 2023/12/27 17:28
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public User getById(String id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
