package com.unravel.demo.deadlock;

public class DeadlockSimulator {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println("Method1: acquired lock1 and lock2");
            }
        }
    }

    public void method2() {
        synchronized (lock1) { // Ensure same order to prevent deadlock
            synchronized (lock2) {
                System.out.println("Method2: acquired lock1 and lock2");
            }
        }
    }

    public static void main(String[] args) {
        DeadlockSimulator simulator = new DeadlockSimulator();
        Thread t1 = new Thread(simulator::method1);
        Thread t2 = new Thread(simulator::method2);

        t1.start();
        t2.start();
    }
}
