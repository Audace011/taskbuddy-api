package com.taskbuddy.taskbuddyapi.controller;

import com.taskbuddy.taskbuddyapi.model.Task;
import com.taskbuddy.taskbuddyapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * TaskController — REST API Layer for Tasks
 *
 * This is the entry point for all task-related HTTP requests from the frontend.
 * It receives requests, delegates to TaskService, and returns HTTP responses.
 *
 * @RestController = @Controller + @ResponseBody (auto-converts Java objects to JSON)
 * @RequestMapping sets the base path for all endpoints in this controller
 *
 * Available endpoints:
 *   GET    /api/tasks?email={email}  → get all tasks for a user
 *   POST   /api/tasks                → create a new task
 *   PUT    /api/tasks/{id}           → update an existing task
 *   DELETE /api/tasks/{id}           → delete a task
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://task-buddy-one-rose.vercel.app"
})
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * GET /api/tasks?email={email}
     * Retrieves all tasks belonging to the specified user.
     *
     * Example: GET /api/tasks?email=karenaudace@gmail.com
     * Response: 200 OK + JSON array of tasks
     *
     * @param email the user's email passed as a query parameter
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam String email) {
        List<Task> tasks = taskService.getTasksByUser(email);
        return ResponseEntity.ok(tasks);
    }

    /**
     * POST /api/tasks
     * Creates a new task. The status is automatically set to "todo".
     *
     * Request body (JSON):
     * {
     *   "title": "My task",
     *   "description": "Details...",
     *   "priority": "high",
     *   "dueDate": "2026-05-20",
     *   "userEmail": "karenaudace@gmail.com"
     * }
     *
     * Response: 201 CREATED + the created task with its MongoDB ID
     *
     * @param task the task data from the request body
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/tasks/{id}
     * Updates an existing task. Only sends the fields you want to change.
     *
     * Example to just update status:
     * { "status": "inprogress" }
     *
     * Response: 200 OK + updated task, or 404 if ID not found
     *
     * @param id          the MongoDB ID of the task to update
     * @param updatedTask the new field values (partial update supported)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        Optional<Task> result = taskService.updateTask(id, updatedTask);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * DELETE /api/tasks/{id}
     * Deletes a task permanently.
     *
     * Response: 204 NO CONTENT if deleted, 404 if not found
     *
     * @param id the MongoDB ID of the task to delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        if (taskService.deleteTask(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
