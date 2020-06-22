package com.spring.security.jsonwebtoken;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class UsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ConfigurationOfJWT configurationOfJWT;
    private final SecretKey secretKeyOfJWT;

    public UsernameAndPasswordAuthFilter(AuthenticationManager authenticationManager, ConfigurationOfJWT configurationOfJWT, SecretKey secretKeyOfJWT) {
        this.authenticationManager = authenticationManager;
        this.configurationOfJWT = configurationOfJWT;
        this.secretKeyOfJWT = secretKeyOfJWT;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthReq requestAuth = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthReq.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(requestAuth.getUsername(), requestAuth.getPassword());
            return authenticationManager.authenticate(authentication);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Generate the Token in order to be retrieved by client for all subsequent requests.
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(configurationOfJWT.getExpirationOfToken())))
                .signWith(secretKeyOfJWT)
                .compact();
        response.addHeader(configurationOfJWT.getAuthorizationHeader(), configurationOfJWT.getPrefixOfToken() + token);
    }
}
