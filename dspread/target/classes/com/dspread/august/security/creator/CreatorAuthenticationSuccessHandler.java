/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.security.creator;

import com.dspread.august.common.JsonResult;
import com.dspread.august.model.UserModel;
import com.dspread.august.security.MyUserDetails;
import com.dspread.august.service.creator.CreatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;

public class CreatorAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String forwardUrl;
    private String authCookie;
    private int expiry;
    private CreatorService creatorService;

    public CreatorAuthenticationSuccessHandler(String forwardUrl, String authCookie, int expiry) {
        this.forwardUrl = forwardUrl;
        this.authCookie = authCookie;
        this.expiry = expiry;
    }
    public CreatorAuthenticationSuccessHandler(CreatorService creatorService, String forwardUrl, String authCookie, int expiry) {
        this.creatorService = creatorService;
        this.forwardUrl = forwardUrl;
        this.authCookie = authCookie;
        this.expiry = expiry;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //save to DB
        JsonResult jsonResult = null;

        if(creatorService != null && authentication.getPrincipal() instanceof MyUserDetails){
            MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
            UserModel user = userDetails.getUser();
            jsonResult = new JsonResult(user);
        } else {
            //  deepcode ignore ApiMigration: <comment the reason here>
            Hashtable ht = new Hashtable();
            ht.put("openid", authentication.getName());
            jsonResult = new JsonResult(ht);
        }

        if(!StringUtils.isEmpty(this.authCookie)){
            // deepcode ignore WebCookieMissesCallToSetSecure: <please specify a reason of ignoring this>, deepcode ignore WebCookieMissesCallToSetHttpOnly: <please specify a reason of ignoring this>, deepcode ignore MissingAPI: <please specify a reason of ignoring this>
            Cookie cookie = new Cookie(this.authCookie, encrypt(authentication.getName()));
            cookie.setPath(StringUtils.isEmpty(request.getContextPath()) ?  "/" : request.getContextPath());
            if(this.expiry > 0) {
                cookie.setMaxAge(this.expiry);
            }
            response.addCookie(cookie);
        }
        if(isAjaxRequest(request) || request.getRequestURL().indexOf("/api/") > -1){
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonResult);

            response.getWriter().append(jsonString);
        } else {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", this.forwardUrl);
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

    public static String encrypt(String rawStr){
        return  Encryptors.text("ly.net2018", "e20f62b9d963").encrypt(rawStr);
    }

    public static String decrypt(String encryptedStr){
        return  Encryptors.text("ly.net2018", "e20f62b9d963").decrypt(encryptedStr);
    }
}
