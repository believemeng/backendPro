package com.dspread.august.security.merchant;

import com.dspread.august.model.MerchantModel;
import com.dspread.august.security.merchant.base.JwtUserDetailsService;
import com.dspread.august.security.merchant.base.JwtUserModel;
import com.dspread.august.service.MerchantService;
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
public class MerchantDetailsService extends JwtUserDetailsService {

    @Override
    protected UserDetails customLoadUserByUsername(String username) {
        MyMerchantDetails userDetails = null;

        try {
             JwtUserModel user = userService.getUserByName(username);
            if(user != null) {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("USER"));
                //封装自定义UserDetails类
                userDetails = new MyMerchantDetails(user, authorities);
            } else {
//                throw new UsernameNotFoundException("该用户不存在！");

            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return userDetails;
    }
}
