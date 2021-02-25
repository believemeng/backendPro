package com.dspread.august.security.merchant.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *  查询用户关联角色信息
 */
@Component
public abstract class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    protected JwtUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customLoadUserByUsername(username);
    }

    protected abstract UserDetails customLoadUserByUsername(String username);
}
