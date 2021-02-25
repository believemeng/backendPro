package com.dspread.august.security;

import com.dspread.august.model.UserModel;
import com.dspread.august.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthProvider implements AuthenticationProvider {

    UserService userService;

    public AuthProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken token
                = (UsernamePasswordAuthenticationToken) authentication;
        String openid = token.getName();
        String unionid = (String)token.getCredentials();

        UserModel user = userService.login(openid,unionid);
        if(user == null){
            throw new UsernameNotFoundException("登录出错");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        //封装自定义UserDetails类
        UserDetails userDetails = new MyUserDetails(user, authorities);
        //授权
        return new UsernamePasswordAuthenticationToken(userDetails, openid, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
    public String encryptPassword(String rawPassoword){
        return rawPassoword;
    }
}
