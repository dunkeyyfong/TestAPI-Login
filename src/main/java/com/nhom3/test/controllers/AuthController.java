package com.nhom3.test.controllers;

import com.nhom3.test.configs.SecurityConfig;
import com.nhom3.test.entities.User;
import com.nhom3.test.services.UserService;
import com.nhom3.test.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/sign-up")
    public Map<String, String> signUp(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists.");
        }

        // Normalize email to lowercase
        user.setEmail(user.getEmail().trim().toLowerCase());

        // Hash the password using the PasswordEncoder bean
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("Sign-Up Hashed Password: " + hashedPassword);
        user.setPassword(hashedPassword);

        // Save the user to the database
        userService.createUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully.");
        return response;
    }

    @PostMapping("/sign-in")
    public Map<String, String> signIn(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email").trim().toLowerCase();
        String password = credentials.get("password");

        // Fetch user from the database
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("Invalid email or password.");
        }

        // Verify password using the PasswordEncoder bean
        boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Is Password Valid: " + isPasswordValid);

        if (!isPasswordValid) {
            throw new RuntimeException("Invalid password.");
        }

        // Generate JWT token if the password is valid
        String token = JWTUtil.generateToken(email);

        // Return the token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}

