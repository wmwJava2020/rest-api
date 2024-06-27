/**
 * Created by:AIDA
 * Date : 6/20/2024
 * Time : 10:51 AM
 */
package com.example.userApp.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import com.example.userApp.modle.request.UserLoginRequestModle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(AuthenticationManager authenticationManager) {

        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {

            // Object mapper would map json-payload form POSTMAN-UI to "UserLongIn" java class object
            // "req" is input parameter used to read json-input from UserLoginRequestModle.class
            UserLoginRequestModle creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequestModle.class);

            LOGGER.info("User mapped from ObjectMapper" ,creds);

            return getAuthenticationManager().authenticate(//the framework locates user details from UserLoginRequestModle.class
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // on success attemptAuthentication, the framework invokes this method & we decided what to be returned like a token or ...
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        //Get secret, encode, and return as bytes
        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.TOKEN_SECRET.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes,SignatureAlgorithm.HS512.getJcaName());

        Instant now = Instant.now();

        String userName = ((User) auth.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(userName)
                        .setExpiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
                                .setIssuedAt(Date.from(now)).signWith(secretKey,SignatureAlgorithm.HS512).compact();
        LOGGER.info("JWT-Generated!",token);
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }

}


