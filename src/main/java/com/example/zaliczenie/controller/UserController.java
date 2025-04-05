package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.User;
import com.example.zaliczenie.repository.UserRepository;
import com.example.zaliczenie.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            return "Username and password cannot be empty";
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return "Login successful. Token: " + token;
        } else {
            return "Invalid username or password";
        }
    }
    @PostMapping("/validate")
    public Boolean validateToken(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token);
        return username != null && jwtUtil.validateToken(token, username);
    }
}