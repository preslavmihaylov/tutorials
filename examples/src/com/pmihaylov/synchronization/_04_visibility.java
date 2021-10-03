package com.pmihaylov.synchronization;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _04_visibility {
    public static void main(String[] args) {
        testVisibility(new UnsyncVisibility());
        testVisibility(new AtomicVisibility());
    }

    public static void testVisibility(Visibility v) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(v::run);
        executor.execute(v::initialize);

        Instant starts = Instant.now();

        executor.shutdown();
        ignoreCheckedExceptions(() -> executor.awaitTermination(5, TimeUnit.SECONDS));

        Instant ends = Instant.now();
        if (Duration.between(starts, ends).getSeconds() > 4) {
            System.out.println(v.getClass().getSimpleName() + ": Operation timed out! There was an infinite loop");
        } else {
            System.out.println(v.getClass().getSimpleName() + ": Operation completed successfully!");
        }
    }

    interface Visibility {
        void run();
        void initialize();
    }

    static class UnsyncVisibility implements Visibility {
        private boolean ready;
        private int number;

        public void run() {
            while (!ready) {}

            if (number != 42) {
                System.out.println("Got the wrong number! Expected: 42, Actual: " + number);
            }
        }

        public void initialize() {
            ignoreCheckedExceptions(() -> Thread.sleep(1000));

            number = 42;
            ready = true;
        }
    }

    static class AtomicVisibility implements Visibility {
        private final AtomicBoolean ready = new AtomicBoolean(false);
        private final AtomicInteger number = new AtomicInteger(0);

        public void run() {
            while (!ready.get()) {}

            if (number.get() != 42) {
                System.out.println("Got the wrong number! Expected: 42, Actual: " + number);
            }
        }

        public void initialize() {
            ignoreCheckedExceptions(() -> Thread.sleep(1000));

            number.set(42);
            ready.set(true);
        }
    }
}
