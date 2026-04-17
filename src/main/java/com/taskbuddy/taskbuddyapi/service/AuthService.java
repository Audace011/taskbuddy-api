package com.taskbuddy.taskbuddyapi.service;

import com.taskbuddy.taskbuddyapi.model.User;
import com.taskbuddy.taskbuddyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthService — Authentication Business Logic
 *
 * Handles user registration and login.
 *
 * Key security concept: BCrypt password hashing
 *   - When registering: the plain password is hashed before saving
 *   - When logging in: the entered password is compared to the stored hash
 *   - BCrypt is a one-way hash — you CANNOT reverse it to get the original password
 *   - Even if the database is compromised, passwords stay safe
 *
 * Why BCryptPasswordEncoder?
 *   - Industry standard for password storage
 *   - Automatically adds a "salt" (random data) to prevent rainbow table attacks
 *   - Slow by design, making brute-force attacks expensive
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    /**
     * BCryptPasswordEncoder from spring-security-crypto.
     * Used to hash passwords on register and verify them on login.
     * The default strength factor is 10 (good balance of security vs speed).
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Register a new user.
     * Checks for duplicate email, hashes the password, then saves to MongoDB.
     *
     * @param name     the user's display name
     * @param email    the user's email (must be unique)
     * @param password the plain-text password (will be hashed before saving)
     * @return the saved User object (without password returned to client)
     * @throws RuntimeException if the email is already registered
     */
    public User register(String name, String email, String password) {
        // Business rule: each email can only be registered once
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("This email is already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // IMPORTANT: Always hash the password — never store plain text!
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    /**
     * Authenticate a user.
     * Looks up the user by email, then checks if the password matches the stored hash.
     *
     * @param email    the email to look up
     * @param password the plain-text password to verify
     * @return Optional<User> — present if credentials are valid, empty if invalid
     */
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                // passwordEncoder.matches() compares plain text to the BCrypt hash
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
