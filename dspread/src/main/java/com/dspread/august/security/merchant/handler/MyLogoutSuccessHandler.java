package com.dspread.august.security.merchant.handler;

import com.dspread.august.common.JsonResult;
import com.dspread.august.security.merchant.base.JwtUserDetails;
import com.dspread.august.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 注销登录
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        JsonResult jsonResult = new JsonResult(HttpServletResponse.SC_OK
                ,"退出成功",null);
        String jsonString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonResult);
        response.setContentType("Application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        try {
            writer.write(jsonString);
            writer.flush();
            writer.close();
        }catch (IOException o){
            o.printStackTrace();
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}
