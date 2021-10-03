package com.pmihaylov.orchestration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _02_executorService {
    public static void main(String[] args) {
        // TODO: Demo other executor types
        ExecutorService exec = Executors.newCachedThreadPool();

        exec.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("Printing from thread %s: #%d\n", Thread.currentThread().getName(), i);
                ignoreCheckedExceptions(() -> Thread.sleep(500));
            }
        });

        exec.submit(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("Printing from thread %s: #%d\n", Thread.currentThread().getName(), i);
                ignoreCheckedExceptions(() -> Thread.sleep(500));
            }
        });

        ignoreCheckedExceptions(() -> Thread.sleep(2000));
        System.out.println("shutting down executor...");

        // TODO: Mention caveats about canceling threads...
        exec.shutdownNow();
        ignoreCheckedExceptions(() -> exec.awaitTermination(5, TimeUnit.SECONDS));
    }
}
