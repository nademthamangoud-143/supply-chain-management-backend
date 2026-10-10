package com.supplychain.service;

import com.supplychain.entity.User;
import com.supplychain.repository.UserRepository;
import com.supplychain.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        return userRepository.save(user);
    }
public String login(String username, String password) {

    System.out.println("LOGIN DEBUG: username received = " + username);
    System.out.println("LOGIN DEBUG: password provided = "
            + (password != null && !password.isBlank()));

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                System.out.println("LOGIN DEBUG: User not found in database");
                return new RuntimeException("Invalid username or password");
            });

    System.out.println("LOGIN DEBUG: User found in database");
    System.out.println("LOGIN DEBUG: Stored password hash exists = "
            + (user.getPassword() != null));

    boolean passwordMatches = passwordEncoder.matches(
            password,
            user.getPassword()
    );

    System.out.println("LOGIN DEBUG: Password matches = "
            + passwordMatches);

    if (!passwordMatches) {
        throw new RuntimeException("Invalid username or password");
    }

    return jwtService.generateToken(
            user.getUsername(),
            user.getRole()
    );
}

    public void resetPassword(String username, String newPassword) {

    User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException("User not found")
            );

    user.setPassword(passwordEncoder.encode(newPassword));

    userRepository.save(user);
}
}