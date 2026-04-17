package com.taskbuddy.taskbuddyapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User — Data Model
 *
 * Represents a registered user in the system.
 * @Document links it to the "users" collection in MongoDB.
 * Password is stored as a BCrypt hash (never plain text).
 */
@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    /** User's display name, e.g. "Karen Audace" */
    private String name;

    /** Unique email address used for login */
    private String email;

    /** BCrypt-hashed password — never stored as plain text */
    private String password;
}
