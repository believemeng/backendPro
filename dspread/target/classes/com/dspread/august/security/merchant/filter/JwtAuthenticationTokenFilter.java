package com.dspread.august.security.merchant.filter;

import com.alibaba.druid.util.StringUtils;
import com.dspread.august.common.Constans;
import com.dspread.august.common.StringUtil;
import com.dspread.august.security.ApiAuthenticationEntryPoint;
import com.dspread.august.security.merchant.MerchantDetailsService;
import com.dspread.august.security.merchant.base.JwtUserDetailsService;
import com.dspread.august.util.FilterUtil;
import com.dspread.august.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 确保经过filter为一次请求
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (urlFilter(request)){
            chain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(Constans.HEADER);
        if (header == null || !header.startsWith(Constans.BEARER)) {
            getResponse(response,"token不合法！");
            return;
        }
        final String authToken = header.substring(Constans.BEARER.length());
        if(JwtTokenUtil.isTokenExpired(authToken)){
            getResponse(response,"token过期！");
            return;
        }
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        //把用户的信息填充到上下文中
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                    getResponse(response,"token错误！");
                    return;
            }
        }
        logger.info("checking authentication " + username);

        chain.doFilter(request, response);
    }

    private boolean urlFilter(HttpServletRequest request) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean boo = false;
        for(int i = 0; i < FilterUtil.arrUrl.size(); i++){
            String requestURI = request.getRequestURI();
            String pattern = FilterUtil.arrUrl.get(i);
            if(antPathMatcher.match(pattern, requestURI) || pattern.contains(requestURI)){
                boo = true;
                break;
            }
        }
        return boo;
    }

    /**
     *  组装token验证失败的返回
     * @param res
     * @param msg
     * @return
     */
    private HttpServletResponse getResponse(HttpServletResponse res,String msg){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("code", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        map.put("msg", msg);
        res.setContentType("Application/json;charset=UTF-8");
        Writer writer;
        try {
            writer = res.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(map));
            writer.flush();
            writer.close();
        }catch (Exception o){
            o.printStackTrace();
        }
        return res;
    }

}
