package com.taskbuddy.taskbuddyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig — Cross-Origin Resource Sharing Configuration
 *
 * What is CORS?
 *   When your Vue frontend (on Vercel: task-buddy-one-rose.vercel.app)
 *   makes API calls to your Spring Boot backend (on a different domain),
 *   the browser blocks these requests by default as a security measure.
 *   This is called the "Same-Origin Policy".
 *
 *   CORS is the mechanism that tells the browser:
 *   "It's OK — these specific origins are allowed to call this API."
 *
 * Without this config → browser will show "CORS error" and block all API calls.
 * With this config    → the frontend can freely communicate with the backend.
 *
 * @Configuration tells Spring this class contains configuration beans
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // Only these origins are allowed to call the API
                        .allowedOrigins(
                                "https://task-buddy-one-rose.vercel.app",  // your live Vercel app
                                "http://localhost:5173",                   // Vue dev server
                                "http://localhost:3000"                    // alternative dev port
                        )
                        // Allow these HTTP methods
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Allow all headers (including Content-Type, Authorization, etc.)
                        .allowedHeaders("*")
                        // Set how long browsers can cache the CORS preflight response
                        .maxAge(3600);
            }
        };
    }
}
