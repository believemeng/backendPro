package com.dspread.august.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class MyPreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

    public static final String SSO_TOKEN = "X-Auth-Token";
    public static final String SSO_CREDENTIALS = "N/A";

    private String authCookie = "";

    public MyPreAuthenticatedProcessingFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    public MyPreAuthenticatedProcessingFilter(AuthenticationManager authenticationManager, String cookie) {
        setAuthenticationManager(authenticationManager);
        this.authCookie = cookie;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String token = request.getHeader(SSO_TOKEN);
        if(StringUtils.isEmpty(token) && !StringUtils.isEmpty(this.authCookie)) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0){
                for(int i=0; i<cookies.length; i++){
                    if(cookies[i].getName().equals(this.authCookie)){
                        token = cookies[i].getValue();
                        break;
                    }
                }
            }
        }
        if(!StringUtils.isEmpty(token)){
            try {
                token = MyAuthenticationSuccessHandler.decrypt(token);
            }catch (Exception ex){
                logger.error("decrypt auth token fail.", ex);
                token = null;
            }
        }
        return token;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return SSO_CREDENTIALS;
    }
}
