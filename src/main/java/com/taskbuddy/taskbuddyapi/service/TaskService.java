package com.taskbuddy.taskbuddyapi.service;

import com.taskbuddy.taskbuddyapi.model.Task;
import com.taskbuddy.taskbuddyapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TaskService — Business Logic Layer
 *
 * This layer sits BETWEEN the Controller and the Repository.
 * The Controller receives the HTTP request and calls this service.
 * This service applies business rules, then calls the repository to access MongoDB.
 *
 * Why have a service layer?
 *   - Keeps controllers thin (only handle HTTP)
 *   - Centralizes business rules in one place
 *   - Makes code easier to test
 *
 * @Service tells Spring to register this as a managed component (bean)
 * @Autowired injects the TaskRepository automatically
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieve all tasks belonging to a specific user.
     * @param userEmail the email of the logged-in user
     * @return list of that user's tasks
     */
    public List<Task> getTasksByUser(String userEmail) {
        return taskRepository.findByUserEmail(userEmail);
    }

    /**
     * Create a new task.
     * Always sets initial status to "todo" regardless of what was sent.
     * @param task the task data from the request body
     * @return the saved task with its generated MongoDB ID
     */
    public Task createTask(Task task) {
        // Business rule: new tasks always start as "todo"
        task.setStatus("todo");
        return taskRepository.save(task);
    }

    /**
     * Update an existing task (partial update — only updates non-null fields).
     * @param id the task ID to update
     * @param updatedTask the new field values
     * @return Optional containing the updated task, or empty if not found
     */
    public Optional<Task> updateTask(String id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            // Only update fields that were actually provided (not null)
            if (updatedTask.getTitle() != null)       existingTask.setTitle(updatedTask.getTitle());
            if (updatedTask.getDescription() != null) existingTask.setDescription(updatedTask.getDescription());
            if (updatedTask.getPriority() != null)    existingTask.setPriority(updatedTask.getPriority());
            if (updatedTask.getDueDate() != null)     existingTask.setDueDate(updatedTask.getDueDate());
            if (updatedTask.getStatus() != null)      existingTask.setStatus(updatedTask.getStatus());

            return taskRepository.save(existingTask);
        });
    }

    /**
     * Delete a task by its ID.
     * @param id the ID of the task to delete
     * @return true if deleted successfully, false if task not found
     */
    public boolean deleteTask(String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
