package com.example.foodservice.auth;

import com.example.foodservice.data.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by NhanNguyen on 4/28/2021
 *
 * @author: NhanNguyen
 * @date: 4/28/2021
 */
public class UserPrincipal extends User implements UserDetails {

    public UserPrincipal(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> lstGrant = new ArrayList<>();
        super.getRoles().forEach(role -> {
            lstGrant.add(new SimpleGrantedAuthority(role.getName()));
            role.getLstPermission().forEach(permission -> {
                lstGrant.add(new SimpleGrantedAuthority(permission.getName()));
            });
        });
        return lstGrant;
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnable();
    }
}
