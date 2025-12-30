package com.kopa.controller;

import com.kopa.model.User;
import com.kopa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    // -------- REGISTER --------
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        // Check if username exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        // Check if email exists
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }

        // Save user
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // -------- LOGIN --------
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password");
        }
    }
}
