package com.unravel.demo.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    public synchronized String login(String userId) {
        return sessions.computeIfAbsent(userId, id -> {
            String sessionId = "SESSION_" + UUID.randomUUID();
            System.out.println("Login successful for " + id + ". Session ID: " + sessionId);
            return sessionId;
        });
    }

    public synchronized String logout(String userId) {
        if (sessions.remove(userId) != null) {
            return "Logout successful.";
        } else {
            return "User not logged in.";
        }
    }

    public String getSessionDetails(String userId) {
        String session = sessions.get(userId);
        if (session == null) {
            throw new RuntimeException("Session not found for user " + userId);
        }
        return "Session ID for user " + userId + ": " + session;
    }
}
