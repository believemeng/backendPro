package com.dspread.august.security.merchant.provider;

import com.alibaba.druid.util.StringUtils;
import com.dspread.august.common.ErrorCode;
import com.dspread.august.exception.CustomerException;
import com.dspread.august.security.merchant.base.JwtUserDetails;
import com.dspread.august.security.merchant.base.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 自定义拦截
 * 用户登录验证用户信息
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {//implements AuthenticationProvider {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //用户输入的用户名
        String username = String.valueOf(authentication.getName());
        //用户输入的密码
        String password = String.valueOf(authentication.getCredentials());
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new BadCredentialsException("用户名或密码为空！");
        }
        //通过自定义的CustomUserDetailsService，以用户输入的用户名查询用户信息
        JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);

        if (userDetails == null){
//            throw new BadCredentialsException("用户没有注册");
            throw new CustomerException(ErrorCode.ERROR_NO_USER,"用户没有注册");
        }

        //用户输入密码加密后与数据库比较
        if(!password.equalsIgnoreCase(userDetails.getPassword())){
//            throw new BadCredentialsException("密码错误！");
            throw new CustomerException(ErrorCode.ERROR_PASSWORD_ERROR,"密码错误！");

        }
        Object principalToReturn = userDetails;
        //将用户信息塞到SecurityContext中，方便获取当前用户信息  把当前用户信息放入Security全局缓存中
        return this.createSuccessAuthentication(principalToReturn, authentication, userDetails);
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
