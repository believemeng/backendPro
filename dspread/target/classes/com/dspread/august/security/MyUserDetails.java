package com.dspread.august.security;
import com.dspread.august.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {


    // 用户信息
    private UserModel user;
    // 用户角色
    private Collection<? extends GrantedAuthority> authorities;

    public UserModel getUser() {
        return user;
    }

    public MyUserDetails(UserModel user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;

        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUnionId();
    }

    @Override
    public String getUsername() {
        return user.getOpenId();
    }
    //账号锁定
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //登录过期
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

    public long getCurrentUserId(){
        return user.getUserId();
    }

}
