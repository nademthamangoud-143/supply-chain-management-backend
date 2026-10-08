package com.supplychain.controller;

import com.supplychain.entity.User;
import com.supplychain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {

        User savedUser = authService.register(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        String token = authService.login(username, password);

        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }
}