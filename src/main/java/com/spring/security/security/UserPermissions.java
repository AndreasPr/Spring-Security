package com.spring.security.security;

public enum UserPermissions {

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    CONTRACT_READ("contract:read"),
    CONTRACT_WRITE("contract:write");

    private final String userPermission;

    UserPermissions(String userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserPermission(){
        return userPermission;
    }
}
