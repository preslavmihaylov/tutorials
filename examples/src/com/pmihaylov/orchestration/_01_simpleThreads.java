package com.pmihaylov.orchestration;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _01_simpleThreads {
    public static void main(String[] args) {
	    new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("Printing from thread %s: #%d\n", Thread.currentThread().getName(), i);
                ignoreCheckedExceptions(() -> Thread.sleep(500));
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.printf("Printing from thread %s: #%d\n", Thread.currentThread().getName(), i);
                ignoreCheckedExceptions(() -> Thread.sleep(500));
            }
        }).start();

        ignoreCheckedExceptions(() -> Thread.sleep(5000));

        // TODO: Problems?
        // * thread management
        // * UX - scheduling tasks, getting results back
    }
}
