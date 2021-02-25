package com.dspread.august.security.merchant;

import com.dspread.august.security.merchant.base.JwtUserDetails;
import com.dspread.august.security.merchant.base.JwtUserModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyMerchantDetails extends JwtUserDetails {

    public MyMerchantDetails(JwtUserModel user, Collection<? extends GrantedAuthority> authorities) {
        super(user, authorities);
    }
}
