package com.spring.security.authentication.services;

import com.spring.security.authentication.repositories.UserRepositoryDataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAuthentication implements UserDetailsService {

    private final UserRepositoryDataAccess userRepositoryDataAccess;

    @Autowired
    public UserServiceAuthentication(@Qualifier("dataSample") UserRepositoryDataAccess userRepositoryDataAccess) {
        this.userRepositoryDataAccess = userRepositoryDataAccess;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoryDataAccess.chooseUserBasedByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found!"));
    }
}
