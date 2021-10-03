package com.pmihaylov;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Common {
    public interface RunnableWithException {
        void run() throws Exception;
    }

    public static void ignoreCheckedExceptions(RunnableWithException r) {
        try {
            r.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T ignoreCheckedExceptions(Callable<T> c) {
        try {
            return c.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void parallelize(int executionsCnt, Runnable ...rs) {
        ExecutorService svc = Executors.newFixedThreadPool(10);
        for (int i = 0; i < executionsCnt; i++) {
            for (Runnable r : rs) {
                svc.execute(r);
            }
        }

        svc.shutdown();
        ignoreCheckedExceptions(() -> svc.awaitTermination(60, TimeUnit.SECONDS));
    }
}
