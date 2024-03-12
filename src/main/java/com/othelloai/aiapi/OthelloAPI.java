package com.othelloai.aiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OthelloAPI {
    public static void main(String[] args) {
        // Start Spring Boot application
        SpringApplication.run(OthelloAPI.class, args);

        // Start JavaFX application on its own thread
        new Thread(() -> HelloApplication.launchApplication(new String[0])).start();
    }
}