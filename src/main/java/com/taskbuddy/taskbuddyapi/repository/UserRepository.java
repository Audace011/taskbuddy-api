package com.taskbuddy.taskbuddyapi.repository;

import com.taskbuddy.taskbuddyapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * UserRepository — Data Access Layer for Users
 *
 * Extends MongoRepository for standard CRUD operations.
 * Custom methods below are auto-implemented by Spring Data MongoDB
 * based on their method names — no SQL or query code needed!
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a user by their email address.
     * Used during login to look up the user.
     * @param email the user's email
     * @return Optional<User> — present if found, empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if an email is already registered.
     * Used during registration to prevent duplicates.
     * @param email the email to check
     * @return true if a user with this email already exists
     */
    boolean existsByEmail(String email);
}
