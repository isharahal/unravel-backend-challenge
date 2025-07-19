package com.unravel.demo.controller;

import com.unravel.demo.concurrency.PriorityLogProcessor;
import com.unravel.demo.concurrency.Task;
import com.unravel.demo.db.DatabaseManager;
import com.unravel.demo.deadlock.DeadlockSimulator;
import com.unravel.demo.memory.MemoryManager;
import com.unravel.demo.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;

@RestController
@RequestMapping("/session")
public class SessionConroller {
    @Autowired
    private PriorityLogProcessor processor;

    private final SessionManager sessionManager = new SessionManager();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userId) {
        String sessionId = sessionManager.login(userId);
        return ResponseEntity.ok("User logged in with session ID: " + sessionId);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String userId) {
        String message = sessionManager.logout(userId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getSession(@PathVariable String userId) {
        try {
            String session = sessionManager.getSessionDetails(userId);
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/dbtest")
    public ResponseEntity<String> testDbConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("Connection acquired, holding for 10 seconds...");
            Thread.sleep(10000);
            return ResponseEntity.ok("Database connection acquired successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to acquire DB connection: " + e.getMessage());
        }
    }

    @PostMapping("/memory/allocate")
    public ResponseEntity<String> allocateMemory(@RequestParam String sessionId) {
        MemoryManager.addSessionData(sessionId);
        return ResponseEntity.ok("Allocated 10MB memory for session: " + sessionId);
    }

    @PostMapping("/memory/release")
    public ResponseEntity<String> releaseMemory(@RequestParam String sessionId) {
        MemoryManager.removeSessionData(sessionId);
        return ResponseEntity.ok("Released memory for session: " + sessionId);
    }

    @PostMapping("/producer/add")
    public ResponseEntity<String> addTask(
            @RequestParam String name,
            @RequestParam int priority) {
        processor.produce(new Task(name, priority));
        return ResponseEntity.ok("Task added: " + name + " with priority: " + priority);
    }

    @GetMapping("/consumer/consume")
    public ResponseEntity<String> consumeTask() throws InterruptedException {
        Task task = processor.consume();
        return ResponseEntity.ok("Consumed task: " + task.getName() + " with priority: " + task.getPriority());
    }

    @GetMapping("/deadlock/test")
    public ResponseEntity<String> testDeadlock() {
        DeadlockSimulator simulator = new DeadlockSimulator();
        Thread t1 = new Thread(simulator::method1);
        Thread t2 = new Thread(simulator::method2);
        t1.start();
        t2.start();
        return ResponseEntity.ok("Deadlock prevention test started. Check logs for confirmation.");
    }

}
