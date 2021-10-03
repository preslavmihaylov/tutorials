package com.pmihaylov.orchestration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _03_scheduledExecutor {
    public static void main(String[] args) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);

        AtomicInteger cnt1 = new AtomicInteger(0);
        exec.scheduleAtFixedRate(() -> {
            System.out.printf("Printing from thread %s: %d\n", Thread.currentThread().getName(), cnt1.get());
            cnt1.incrementAndGet();
        }, 0,500, TimeUnit.MILLISECONDS);

        AtomicInteger cnt2 = new AtomicInteger(0);
        exec.scheduleAtFixedRate(() -> {
            System.out.printf("Printing from thread %s: %d\n", Thread.currentThread().getName(), cnt2.get());
            cnt2.incrementAndGet();
        }, 0, 500, TimeUnit.MILLISECONDS);

        ignoreCheckedExceptions(() -> Thread.sleep(2000));
        System.out.println("shutting down executor...");

        exec.shutdownNow();
        ignoreCheckedExceptions(() -> exec.awaitTermination(5, TimeUnit.SECONDS));
    }
}
