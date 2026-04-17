package com.taskbuddy.taskbuddyapi.repository;

import com.taskbuddy.taskbuddyapi.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * TaskRepository — Data Access Layer
 *
 * Extends MongoRepository which gives us FREE built-in methods:
 *   - save(task)         → insert or update
 *   - findById(id)       → find one by ID
 *   - findAll()          → get all tasks
 *   - deleteById(id)     → remove by ID
 *   - existsById(id)     → check if exists
 *
 * We also define a CUSTOM query method:
 *   findByUserEmail → Spring Data automatically generates the MongoDB query:
 *   db.tasks.find({ userEmail: "..." })
 */
public interface TaskRepository extends MongoRepository<Task, String> {

    /**
     * Find all tasks belonging to a specific user.
     * Spring Data auto-implements this based on the method name.
     * @param userEmail the email address of the task owner
     * @return list of tasks owned by that user
     */
    List<Task> findByUserEmail(String userEmail);
}
