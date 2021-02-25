package com.dspread.august.security.merchant.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


public class JwtUserDetails implements UserDetails, Serializable {

    protected JwtUserModel user;
    //存放用户的角色信息
    protected Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(){}

    public JwtUserDetails(JwtUserModel user, Collection<? extends GrantedAuthority> authorities) {
      this.user = user;
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "username='" + user.getUsername() + '\'' +
                ", password='" + user.getPassword() + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public JwtUserModel getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //账号是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    ////账号是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    ///账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
