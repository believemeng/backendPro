package com.dspread.august.security;


import com.dspread.august.exception.CustomerException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(Util.IsAjaxRequest(request)){
            Util.SendAjaxError(response,1, exception.getMessage());
        } else {
            if (exception instanceof CustomerException){
               Util.SendUserVerifyError(request,response, (CustomerException) exception);
            }else {
                super.onAuthenticationFailure(request,response,exception);
            }

        }

    }
}
