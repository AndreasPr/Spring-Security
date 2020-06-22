package com.spring.security.authentication.repositories;

import com.spring.security.authentication.domain.User;

import java.util.Optional;

public interface UserRepositoryDataAccess {
    Optional<User> chooseUserBasedByUsername(String username);
}
