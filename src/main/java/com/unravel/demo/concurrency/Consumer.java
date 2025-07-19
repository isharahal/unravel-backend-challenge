package com.unravel.demo.concurrency;

public class Consumer extends Thread {
    private final PriorityLogProcessor processor;

    Consumer(PriorityLogProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        try {
            while (true) {
                processor.consume();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
