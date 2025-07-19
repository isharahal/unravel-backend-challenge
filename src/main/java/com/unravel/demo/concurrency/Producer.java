package com.unravel.demo.concurrency;

public class Producer extends Thread {
    private final PriorityLogProcessor processor;

    Producer(PriorityLogProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            processor.produce(new Task("Task" + i, i % 3));
        }
    }
}
