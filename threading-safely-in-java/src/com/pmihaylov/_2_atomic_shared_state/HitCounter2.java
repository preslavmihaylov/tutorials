package com.pmihaylov._2_atomic_shared_state;

public class HitCounter2 {
    private int counter = 0;

    public synchronized void hit() {
        counter++;
    }

    public synchronized int get() {
        return counter;
    }
}
