/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.security.creator;

import com.dspread.august.security.ApiAuthenticationEntryPoint;
import com.dspread.august.security.MyAuthenticationFailureHandler;
import com.dspread.august.security.MyPreAuthenticatedProcessingFilter;
import com.dspread.august.service.creator.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
// file deepcode ignore DisablesCSRFProtection: <comment the reason here>
@Order(2)
@Configuration
public class CreatorSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CreatorUserDetailsService creatorUserDetailsService;
    @Autowired
    CreatorService creatorService;
    @Autowired
    CreatorAuthUserDetailsService creatorAuthUserDetailsService;

    @Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.creatorUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        //自定义AuthenticationProvider
        auth.authenticationProvider(new CreatorAuthProvider(creatorService))
                .authenticationProvider(preAuthenticationProvider());
    }

    private AuthenticationProvider preAuthenticationProvider() {

        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(creatorAuthUserDetailsService);

        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/creator/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new MyPreAuthenticatedProcessingFilter(authenticationManager(), "creator_auth_token"))
                .authorizeRequests()
                .antMatchers("/api/creator/user/login").permitAll()
                .antMatchers("/api/creator/**").hasAuthority("CREATOR")

                .and()
                .httpBasic()
                .authenticationEntryPoint(new ApiAuthenticationEntryPoint())

                .and()
                .formLogin()
                .loginPage("/api/creator/user/login")
                .loginProcessingUrl("/api/creator/user/login")

                .usernameParameter("openid")
                .passwordParameter("unionid")
                .successHandler(new CreatorAuthenticationSuccessHandler(creatorService, "", "creator_auth_token", 30 * 24 * 3600))
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/api/creator/user/logout")
                .deleteCookies("JSESSIONID", "creator_auth_token");
    }
}
