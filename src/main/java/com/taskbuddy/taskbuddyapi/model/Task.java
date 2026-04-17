package com.taskbuddy.taskbuddyapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Task — Data Model
 *
 * This class represents a task in the system.
 * @Document links it to the "tasks" collection in MongoDB.
 * @Id marks the primary key field.
 * @Data (from Lombok) auto-generates: getters, setters, toString, equals, hashCode
 *
 * Field values match the frontend exactly:
 *   priority → "low", "medium", "high"
 *   status   → "todo", "inprogress", "done"
 *   dueDate  → "YYYY-MM-DD" string format
 */
@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;

    private String description;

    /** Priority level: "low", "medium", or "high" */
    private String priority;

    /** Due date in YYYY-MM-DD format, e.g. "2026-05-20" */
    private String dueDate;

    /** Task status: "todo", "inprogress", or "done" */
    private String status;

    /** Email of the user who owns this task — links task to a user */
    private String userEmail;
}
