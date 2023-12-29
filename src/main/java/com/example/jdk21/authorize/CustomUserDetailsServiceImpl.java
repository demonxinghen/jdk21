package com.example.jdk21.authorize;

import com.example.jdk21.exception.BizException;
import com.example.jdk21.model.Permission;
import com.example.jdk21.model.User;
import com.example.jdk21.model.UserPermissionRelation;
import com.example.jdk21.repository.PermissionRepository;
import com.example.jdk21.repository.UserPermissionRelationRepository;
import com.example.jdk21.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2023/12/27 15:55
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private UserPermissionRelationRepository userPermissionRelationRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BizException("User not found");
        }
        List<UserPermissionRelation> userPermissionRelations = userPermissionRelationRepository.findAllByUserId(user.getId());
        List<Permission> permissions = permissionRepository.findAllById(userPermissionRelations.stream().map(UserPermissionRelation::getPermissionId).collect(Collectors.toSet()));
        return new CustomUserDetails(user, permissions.stream().map(Permission::getId).collect(Collectors.toList()));
    }
}
