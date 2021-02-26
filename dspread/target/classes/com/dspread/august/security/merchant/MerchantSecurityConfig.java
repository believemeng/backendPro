package com.dspread.august.security.merchant;

import com.dspread.august.common.AESUtil;
import com.dspread.august.security.*;

import com.dspread.august.security.merchant.filter.JwtAuthenticationTokenFilter;
import com.dspread.august.security.merchant.filter.MyUsernamePasswordAuthenticationFilter;
import com.dspread.august.security.merchant.handler.MyLogoutSuccessHandler;
import com.dspread.august.security.merchant.handler.MySuccessHandler;
import com.dspread.august.security.merchant.provider.JwtAuthenticationProvider;
import com.dspread.august.util.FilterUtil;
import com.google.api.client.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// file deepcode ignore DisablesCSRFProtection: <comment the reason here>
@Configuration
public class MerchantSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider; // 自定义登录


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(jwtAuthenticationProvider);

    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()

                .httpBasic()
                .authenticationEntryPoint(new ApiAuthenticationEntryPoint())


                .and()
                .logout()
                .logoutUrl("/api/merchant/logout")
                .logoutSuccessHandler(new MyLogoutSuccessHandler());



        //用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
    }


    /**
     * JSON登陆（注册登录的bean）
     */
    @Bean
    MyUsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();

        filter.setAuthenticationSuccessHandler(new MySuccessHandler());
        filter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/api/merchant/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


}
