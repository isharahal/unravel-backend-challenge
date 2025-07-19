package com.unravel.demo.concurrency;

import org.springframework.stereotype.Service;

import java.util.concurrent.PriorityBlockingQueue;

@Service
public class PriorityLogProcessor {
    private final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

    public void produce(Task task) {
        queue.put(task);
        System.out.println("Produced: " + task.name);
    }

    public Task consume() throws InterruptedException {
        Task task = queue.take();
        System.out.println("Consumed: " + task.name);
        return task;
    }
}
