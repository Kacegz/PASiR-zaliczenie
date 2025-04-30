package com.example.zaliczenie.controller;

import com.example.zaliczenie.model.User;
import com.example.zaliczenie.repository.UserRepository;
import com.example.zaliczenie.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        if(user.getUsername() == null || user.getPassword() == null || 
           user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            response.put("message", "Username and password cannot be empty");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (userRepository.findByUsername(user.getUsername()) != null) {
            response.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        
        String token = jwtUtil.generateToken(savedUser.getUsername());
        
        response.put("message", "User registered successfully");
        response.put("token", token);
        response.put("username", savedUser.getUsername());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            String token = jwtUtil.generateToken(existingUser.getUsername());
            
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("username", existingUser.getUsername());
            
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("message", "Authorization header is missing or invalid");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            User user = userRepository.findByUsername(username);
            if (user != null) {
                response.put("username", user.getUsername());
                response.put("expiration", jwtUtil.extractClaims(token).getExpiration());
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            response.put("message", "Invalid token");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}