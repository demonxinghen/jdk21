package com.example.jdk21.authorize;

import cn.hutool.core.bean.BeanUtil;
import com.example.jdk21.model.Permission;
import com.example.jdk21.model.User;
import com.example.jdk21.model.UserPermissionRelation;
import com.example.jdk21.repository.PermissionRepository;
import com.example.jdk21.repository.UserPermissionRelationRepository;
import com.example.jdk21.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;
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
        User user = userRepository.findByUsernameAndDeletedIsFalse(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        List<UserPermissionRelation> userPermissionRelations = userPermissionRelationRepository.findAllByUserId(user.getId());
        List<Permission> permissions = permissionRepository.findAllById(userPermissionRelations.stream().map(UserPermissionRelation::getPermissionId).collect(Collectors.toSet()));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        BeanUtil.copyProperties(user, customUserDetails);
        customUserDetails.setPermissions(permissions.stream().map(Permission::getId).collect(Collectors.toList()));
        return customUserDetails;
    }
}
