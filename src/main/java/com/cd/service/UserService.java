package com.cd.service;


import com.cd.entity.UserEntity;
import com.cd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register new user with additional fields
    public UserEntity registerUser(UserEntity user) {

        return userRepository.save(user);
    }

    // Get user by username (used for authentication)
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
