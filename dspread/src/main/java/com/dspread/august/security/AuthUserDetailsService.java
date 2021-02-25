package com.dspread.august.security;

import com.dspread.august.model.UserModel;
import com.dspread.august.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class AuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        String openid = (String) token.getPrincipal();

        if(!StringUtils.isEmpty(openid)) {
            UserModel user = userService.getUserByOpenId(openid);
            if (user != null) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("USER"));
                MyUserDetails userDetails = new MyUserDetails(user, authorities);
                return userDetails;
            }
        }
        throw new UsernameNotFoundException("用户不存在");
    }
}
