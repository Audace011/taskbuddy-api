package com.taskbuddy.taskbuddyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TaskBuddy API — Main Application Entry Point
 *
 * This is the starting point of the Spring Boot application.
 * @SpringBootApplication enables:
 *   - @Configuration: marks this as a configuration class
 *   - @EnableAutoConfiguration: auto-configures Spring Boot
 *   - @ComponentScan: scans all classes in this package and sub-packages
 */
@SpringBootApplication
public class TaskbuddyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskbuddyApiApplication.class, args);
    }
}
