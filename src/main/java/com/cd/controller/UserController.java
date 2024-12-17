package com.cd.controller;

import com.cd.dto.LoginRequest;
import com.cd.dto.LoginResponse;
import com.cd.entity.UserEntity;
import com.cd.exception.CustomException;
import com.cd.exception.UserNotFoundException;
import com.cd.service.UserService;
import com.cd.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        UserEntity newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserEntity user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (password.equals(user.getPassword())) {
            String token = jwtUtil.generateToken(username);
            LoginResponse loginResponse = new LoginResponse(token, user.getId());
            return ResponseEntity.ok(loginResponse);
        } else {
            throw new CustomException("Invalid password");
        }
    }
}
