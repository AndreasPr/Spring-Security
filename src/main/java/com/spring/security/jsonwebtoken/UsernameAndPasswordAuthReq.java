package com.spring.security.jsonwebtoken;

public class UsernameAndPasswordAuthReq {

    private String username;
    private String password;

    public UsernameAndPasswordAuthReq() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
