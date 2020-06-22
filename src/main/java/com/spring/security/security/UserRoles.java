package com.spring.security.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.spring.security.security.UserPermissions.*;

public enum UserRoles {

    EMPLOYEE(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(CONTRACT_READ, CONTRACT_WRITE, EMPLOYEE_READ, EMPLOYEE_WRITE)),
    SUPERVISOR(Sets.newHashSet(CONTRACT_READ, EMPLOYEE_READ));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getUserPermissions(){
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getUserPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getUserPermission())).collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority(this.name() + "_ROLE"));
        return permissions;
    }
}
