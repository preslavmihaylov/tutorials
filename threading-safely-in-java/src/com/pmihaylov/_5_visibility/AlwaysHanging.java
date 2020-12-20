package com.pmihaylov._5_visibility;

public class AlwaysHanging {
    private boolean ready;
    private int number;

    public void run() {
        while (!ready) {}

        if (number != 42) {
            System.out.println("Wrong number! Expected: 42, Actual: " + number);
        }
    }

    public void initialize() throws InterruptedException {
        Thread.sleep(1000);

        number = 42;
        ready = true;
    }
}
