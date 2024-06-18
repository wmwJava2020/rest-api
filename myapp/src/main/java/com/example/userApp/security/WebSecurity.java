/**
 * Created by:Wondafrash
 * Date : 6/18/2024
 * Time : 9:21 AM
 */
package com.example.userApp.security;

import com.example.userApp.service.UserService;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                 .csrf()
                 .disable()
                 .authorizeRequests()
                 .antMatchers(HttpMethod.POST, "/users").permitAll()
                 .anyRequest().authenticated();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
     builder.userDetailsService(userService)
             .passwordEncoder(bCryptPasswordEncoder);
    }


}
