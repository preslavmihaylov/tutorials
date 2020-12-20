package com.pmihaylov._2_atomic_shared_state;

public class HitCounter1 {
    private int counter = 0;

    public void hit() {
        counter++;
    }

    public int get() {
        return counter;
    }
}
