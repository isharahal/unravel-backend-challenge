package com.unravel.demo.memory;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryManager {
    private static final Map<String, WeakReference<byte[]>> sessionData = new ConcurrentHashMap<>();

    public static void addSessionData(String sessionId) {
        sessionData.put(sessionId, new WeakReference<>(new byte[10 * 1024 * 1024])); // 10MB per session
        System.out.println("Added session data for " + sessionId);
    }

    public static void removeSessionData(String sessionId) {
        sessionData.remove(sessionId);
        System.out.println("Removed session data for " + sessionId);
    }
}
