package com.dspread.august.security.merchant.base;

public abstract class JwtUserService {
    public abstract JwtUserModel getUserByName(String username);
}
