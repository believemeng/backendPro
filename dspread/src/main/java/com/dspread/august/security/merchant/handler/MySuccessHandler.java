package com.dspread.august.security.merchant.handler;

import com.dspread.august.common.JsonResult;
import com.dspread.august.security.merchant.base.JwtUserDetails;
import com.dspread.august.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 登录成功
 */

public class MySuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MySuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        JwtUserDetails userDetails = (JwtUserDetails)authentication.getPrincipal();
        //生成token
        String token = JwtTokenUtil.generateToken(userDetails);
        Map<String,String> map = new LinkedHashMap<>();
        map.put("code", String.valueOf(HttpServletResponse.SC_OK));
        map.put("msg", "登录成功");
        map.put("data",token);
        response.setContentType("Application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(map));
        writer.flush();
        writer.close();


    }
}
