package com.dspread.august.security.merchant.base;

public class JwtUserModel {

    protected String username;

    protected String password;

    public JwtUserModel(String username, String password) {
        this.password=password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JwtUserModel() {

    }




}
