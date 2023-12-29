package com.example.jdk21.authorize;

import com.example.jdk21.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2023/12/27 15:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CustomUserDetails extends User implements UserDetails {

    private List<String> permissions;

    private String token;

    public CustomUserDetails(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * 权限集合
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
