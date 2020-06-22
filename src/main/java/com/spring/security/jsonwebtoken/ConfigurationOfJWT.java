package com.spring.security.jsonwebtoken;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.jwt")
public class ConfigurationOfJWT {

    private String prefixOfToken;
    private Integer expirationOfToken;
    private String secretKey;

    public ConfigurationOfJWT() {
    }

    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }

    public String getPrefixOfToken() {
        return prefixOfToken;
    }

    public void setPrefixOfToken(String prefixOfToken) {
        this.prefixOfToken = prefixOfToken;
    }

    public Integer getExpirationOfToken() {
        return expirationOfToken;
    }

    public void setExpirationOfToken(Integer expirationOfToken) {
        this.expirationOfToken = expirationOfToken;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
