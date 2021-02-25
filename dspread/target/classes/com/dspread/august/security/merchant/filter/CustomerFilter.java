//package com.dspread.august.security.merchant.filter;
//
//import com.alibaba.druid.util.StringUtils;
//import com.alibaba.fastjson.JSON;
//import com.dspread.august.common.AESUtil;
//import com.dspread.august.common.ErrorCode;
//import com.dspread.august.exception.CustomerException;
//import com.dspread.august.model.SafetyNetModel;
//import com.dspread.august.security.Util;
//import com.dspread.august.security.merchant.base.JwtUserModel;
//import com.dspread.august.util.RequestWrapper;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CustomerFilter implements Filter {
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        RequestWrapper requestWrapper = null;
//
//        AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        if (antPathMatcher.match("/api/getDeviceStatus", request.getRequestURI())) {
//            requestWrapper = new RequestWrapper(request);
//
//            String nonce = (String) request.getSession().getAttribute("nonce");
//            String json = Util.getBodyString(requestWrapper);
//            SafetyNetModel parse = JSON.parseObject(json, SafetyNetModel.class);
//
//            if (StringUtils.isEmpty(nonce) || (parse != null && StringUtils.isEmpty(parse.getNonce())) || !nonce.equalsIgnoreCase(parse.getNonce())) {
//                request.getSession().invalidate();
//                throw new CustomerException(ErrorCode.ERROR_RANDOM_VERIFY, "nonce verify error");
//            }
//            request.getSession().invalidate();
//        }
//
//        if (requestWrapper == null) {
//            filterChain.doFilter(request, response);
//        } else {
//            filterChain.doFilter(requestWrapper, response);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//}
