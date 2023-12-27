package com.example.jdk21.authorize;

import com.example.jdk21.exception.BizException;
import com.example.jdk21.model.User;
import com.example.jdk21.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author admin
 * @date 2023/12/27 15:55
 */
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BizException("User not found");
        }

        return null;
    }
}
