package com.taskbuddy.taskbuddyapi.controller;

import com.taskbuddy.taskbuddyapi.model.User;
import com.taskbuddy.taskbuddyapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * AuthController — REST API Layer for Authentication
 *
 * Handles user registration and login HTTP requests.
 * Note: We never return the password in any response!
 *
 * Available endpoints:
 *   POST /api/auth/register → create a new account
 *   POST /api/auth/login    → login with email + password
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth/register
     * Creates a new user account.
     *
     * Request body (JSON):
     * {
     *   "name": "Karen Audace",
     *   "email": "karenaudace@gmail.com",
     *   "password": "Karenzi123"
     * }
     *
     * Response on success: 201 CREATED
     * {
     *   "id": "abc123...",
     *   "name": "Karen Audace",
     *   "email": "karenaudace@gmail.com"
     * }
     *
     * Response on duplicate email: 409 CONFLICT
     * { "error": "This email is already registered" }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> body) {
        try {
            User user = authService.register(
                    body.get("name"),
                    body.get("email"),
                    body.get("password")
            );

            // Return user info WITHOUT the password
            Map<String, String> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    /**
     * POST /api/auth/login
     * Authenticates a user with email and password.
     *
     * Request body (JSON):
     * {
     *   "email": "karenaudace@gmail.com",
     *   "password": "Karenzi123"
     * }
     *
     * Response on success: 200 OK
     * {
     *   "id": "abc123...",
     *   "name": "Karen Audace",
     *   "email": "karenaudace@gmail.com"
     * }
     *
     * Response on invalid credentials: 401 UNAUTHORIZED
     * { "error": "Invalid email or password" }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        Optional<User> userOpt = authService.login(body.get("email"), body.get("password"));

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Return user info WITHOUT the password
            Map<String, String> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());

            return ResponseEntity.ok(response);
        }

        // Credentials did not match — return 401
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
