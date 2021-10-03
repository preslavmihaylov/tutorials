package com.pmihaylov.synchronization;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class _01_weekendsTests {
    public static void main(String[] args) {
        UnsyncWeekends w1 = new UnsyncWeekends();
        if (isThreadSafe(w1)) {
            System.out.println("UnsyncWeekends is thread-safe!");
        } else {
            System.out.println("UnsyncWeekends is NOT thread-safe!");
        }

        SyncWeekends w2 = new SyncWeekends();
        if (isThreadSafe(w2)) {
            System.out.println("SyncWeekends is thread-safe!");
        } else {
            System.out.println("SyncWeekends is NOT thread-safe!");
        }
    }

    public static boolean isThreadSafe(WeekendsGetter weekendsGetter) {
        ExecutorService exec = Executors.newCachedThreadPool();

        List<Future<Map<Integer, String>>> results = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            results.add(exec.submit(weekendsGetter::getWeekends));
        }

        AtomicInteger violationsCnt = new AtomicInteger(0);
        for (Future<Map<Integer, String>> fr : results) {
            ignoreCheckedExceptions(() -> {
                Map<Integer, String> r = fr.get();
                if (r.size() != 2 || !r.get(6).equals("Saturday") || !r.get(7).equals("Sunday")) {
                    violationsCnt.getAndIncrement();
                }
            });
        }

        exec.shutdown();
        ignoreCheckedExceptions(() -> exec.awaitTermination(60, TimeUnit.SECONDS));

        return violationsCnt.get() == 0;
    }

    interface WeekendsGetter {
        Map<Integer, String> getWeekends();
    }

    static class UnsyncWeekends implements WeekendsGetter {
        public Map<Integer, String> getWeekends() {
            Map<Integer, String> days = new HashMap<>();
            days.put(6, "Saturday");
            days.put(7, "Sunday");

            return days;
        }
    }

    static class SyncWeekends implements WeekendsGetter {
        public Map<Integer, String> getWeekends() {
            Map<Integer, String> days = new ConcurrentHashMap<>();
            days.put(6, "Saturday");
            days.put(7, "Sunday");

            return days;
        }

    }
}
