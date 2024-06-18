/**
 * Created by:Wondafrash
 * Date : 6/18/2024
 * Time : 9:21 AM
 */
package com.example.userApp.security;

import com.example.userApp.service.UserService;
import com.example.userApp.shared.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;


@Configuration
@EnableMethodSecurity// to provide method level security
public class SecurityConfig {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserDetailsService userService;

    UserDTO userDTO;

    @Autowired
    public SecurityConfig(UserDetailsService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean // this is about authentication part
    public UserDetailsService userService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername(passwordEncoder.encode("uName"))
                .password(passwordEncoder.encode("pWord"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername(passwordEncoder.encode("uName"))
                .password(passwordEncoder.encode("pWord"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean // this is about authorization
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/all").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/data").authenticated()
                .and().build();
                //.formLogin().and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
