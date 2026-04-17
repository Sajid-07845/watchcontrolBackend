package com.watchstore.service;

import com.watchstore.model.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByEmail(String email);
    User getUserById(Long id);
}
