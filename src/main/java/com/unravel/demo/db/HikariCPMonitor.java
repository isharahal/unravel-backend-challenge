package com.unravel.demo.db;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.util.Timer;
import java.util.TimerTask;

public class HikariCPMonitor {
    private final HikariDataSource dataSource;
    private final HikariPoolMXBean poolProxy;

    public HikariCPMonitor(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        this.poolProxy = dataSource.getHikariPoolMXBean();
    }

    public void startMonitoring() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int active = poolProxy.getActiveConnections();
                int idle = poolProxy.getIdleConnections();
                int total = poolProxy.getTotalConnections();
                int threadsAwaiting = poolProxy.getThreadsAwaitingConnection();

                System.out.println("[HikariCP Monitor] Active: " + active +
                        ", Idle: " + idle +
                        ", Total: " + total +
                        ", Awaiting: " + threadsAwaiting);

                if (threadsAwaiting > 5) {
                    System.out.println("[HikariCP Monitor] WARNING: High number of threads awaiting connections!");
                }
            }
        }, 0, 10000); // Logs every 10 seconds
    }
}
