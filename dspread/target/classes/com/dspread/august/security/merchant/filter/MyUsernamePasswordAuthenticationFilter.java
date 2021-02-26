package com.dspread.august.security.merchant.filter;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dspread.august.model.MerchantModel;
import com.dspread.august.security.Util;
import com.dspread.august.security.merchant.base.JwtUserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.IllegalPathStateException;
import java.io.*;

/**
 * 登录使用自定义的登录（JSON登录）
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //attempt Authentication when Content-Type is json

        //use jackson to deserialize json

        String  json = Util.getJsonFromRequest(request);
        JwtUserModel parse = JSON.parseObject(json,JwtUserModel.class);
        UsernamePasswordAuthenticationToken authRequest = null;
        try {
            MerchantModel authenticationBean = new MerchantModel(parse.getUsername(), parse.getPassword());
            authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationBean.getUsername(), authenticationBean.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            authRequest = new UsernamePasswordAuthenticationToken(
                    "", "");
        } finally {
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        //transmit it to UsernamePasswordAuthenticationFilter

//            return super.attemptAuthentication(request, response);
    }



}
