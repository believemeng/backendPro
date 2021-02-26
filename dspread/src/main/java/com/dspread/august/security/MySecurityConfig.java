package com.dspread.august.security;

import com.dspread.august.service.UserService;
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
@Order(1)
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    UserService userService;
    @Autowired
    AuthUserDetailsService authUserDetailsService;

    @Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

        //自定义AuthenticationProvider
        auth
                .authenticationProvider(preAuthenticationProvider())
                .authenticationProvider(new AuthProvider(userService));
    }

    private AuthenticationProvider preAuthenticationProvider() {

        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(authUserDetailsService);

        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/sharing/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new MyPreAuthenticatedProcessingFilter(authenticationManager(), "auth_token"))
                .authorizeRequests()
                .antMatchers("/api/sharing/user/login").permitAll()
                .antMatchers("/api/sharing/**").hasAuthority("USER")

                .and()
                .httpBasic()
                .authenticationEntryPoint(new ApiAuthenticationEntryPoint())

                .and()
                .formLogin()
                .loginPage("/api/sharing/user/login")
                .loginProcessingUrl("/api/sharing/user/login")
                .usernameParameter("openid")
                .passwordParameter("unionid")
                .successHandler(new MyAuthenticationSuccessHandler(userService, "", "auth_token", 30 * 24 * 3600))
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/api/sharing/user/logout")
                .deleteCookies("JSESSIONID", "auth_token");
    }
}
