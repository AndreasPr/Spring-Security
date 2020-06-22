package com.spring.security.jsonwebtoken;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@EnableConfigurationProperties(ConfigurationOfJWT.class)
public class SecretKeyOfJWT {

    private final ConfigurationOfJWT configurationOfJWT;

    @Autowired
    public SecretKeyOfJWT(ConfigurationOfJWT configurationOfJWT) {
        this.configurationOfJWT = configurationOfJWT;
    }

    @Bean
    public SecretKey getOurSecretKey(){
        return Keys.hmacShaKeyFor(configurationOfJWT.getSecretKey().getBytes());
    }
}
