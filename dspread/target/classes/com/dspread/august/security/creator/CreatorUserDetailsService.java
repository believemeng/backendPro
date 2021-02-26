/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.security.creator;
import com.dspread.august.model.UserModel;
import com.dspread.august.security.MyUserDetails;
import com.dspread.august.service.creator.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CreatorUserDetailsService implements UserDetailsService {

    @Autowired
    private CreatorService creatorService;

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {

        UserDetails userDetails = null;
        try {
            UserModel user = creatorService.getUserByCreatorOpenId(openid);
            //我是创客
            user.setCreator(true);

            if(user != null) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("CREATOR"));
                //封装自定义UserDetails类
                userDetails = new MyUserDetails(user, authorities);
            } else {
                throw new UsernameNotFoundException("该用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }
}
