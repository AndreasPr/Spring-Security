package com.spring.security.authentication.repositories;

import com.google.common.collect.Lists;
import com.spring.security.authentication.domain.User;
import com.spring.security.security.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("dataSample")
public class UserRepositoryDataAccessImpl implements UserRepositoryDataAccess{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryDataAccessImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> chooseUserBasedByUsername(String username) {
        return getUsers().stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }

    private List<User> getUsers(){
        List<User> users = Lists.newArrayList(
                new User(UserRoles.ADMIN.getSimpleGrantedAuthorities(), passwordEncoder.encode("1234"),
                        "adminUser", true, true, true, true),
                new User(UserRoles.SUPERVISOR.getSimpleGrantedAuthorities(), passwordEncoder.encode("1234"),
                        "supervisorUser", true, true, true, true),
                new User(UserRoles.EMPLOYEE.getSimpleGrantedAuthorities(), passwordEncoder.encode("1234"),
                        "employeeUser", true, true, true, true)
        );
        return users;
    }
}
