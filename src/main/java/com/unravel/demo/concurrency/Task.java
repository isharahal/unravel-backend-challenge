package com.unravel.demo.concurrency;

import lombok.Data;

@Data
public class Task implements Comparable<Task> {
    String name;
    int priority; // lower = higher priority

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.priority, o.priority);
    }
}
