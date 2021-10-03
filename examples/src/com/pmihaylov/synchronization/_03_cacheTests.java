package com.pmihaylov.synchronization;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pmihaylov.Common.parallelize;

public class _03_cacheTests {
    public static void main(String[] args) {
        System.out.println("\nNon-thread-safe example with unsyncCnt cache");
        UnsafeCache unsafeCache = new UnsafeCache();
        testCache(unsafeCache);

        System.out.println("Non-thread-safe example with non-atomic compound actions cache");
        NonAtomicCompoundCache nonAtomicCompoundCache = new NonAtomicCompoundCache();
        testCache(nonAtomicCompoundCache);

        System.out.println("Thread-safe cache example");
        SafeCache threadSafeCache = new SafeCache();
        testCache(threadSafeCache);
    }

    public static void testCache(Cache cache) {
        AtomicInteger cnt1 = new AtomicInteger(0);
        AtomicInteger cnt2 = new AtomicInteger(0);
        parallelize(10000,
                () -> cache.setValue(cnt1.incrementAndGet()),
                () -> cache.setValue(cnt2.incrementAndGet()));

        int violationsCnt = 0;
        for (int i = 1; i <= cnt1.get(); i++) {
            int actual = cache.getValue(i);
            if (actual != 1) {
                violationsCnt++;
            }
        }

        if (violationsCnt == 0) {
            System.out.println("Cache is thread-safe!\n");
        } else {
            System.out.println("Cache is NOT thread-safe with " + violationsCnt + " violations!\n");
        }
    }

    interface Cache {
        int getValue(int key);
        void setValue(int key);
    }

    // Not exactly the same example as in the book, but its the same principle
    // This is an example of using non thread-safe class (HashMap)
    // If the cache is thread-safe, it should increment a key only once
    static class UnsafeCache implements Cache {
        Map<Integer, Integer> cache = new HashMap<>();

        public int getValue(int key) {
            return cache.getOrDefault(key, 0);
        }

        public void setValue(int key) {
            if (!cache.containsKey(key)) {
                cache.put(key, cache.getOrDefault(key, 0) + 1);
            }
        }
    }

    // This uses a thread-safe class, but compound actions aren't synchronized.
    // Hence, the class is still non thread-safe
    static class NonAtomicCompoundCache implements Cache {
        Map<Integer, Integer> cache = new ConcurrentHashMap<>();

        public int getValue(int key) {
            return cache.getOrDefault(key, 0);
        }

        public void setValue(int key) {
            if (!cache.containsKey(key)) {
                cache.put(key, cache.getOrDefault(key, 0) + 1);
            }
        }
    }

    static class SafeCache implements Cache {
        Map<Integer, Integer> cache = new ConcurrentHashMap<>();

        public int getValue(int key) {
            return cache.getOrDefault(key, 0);
        }

        public void setValue(int key) {
            cache.putIfAbsent(key, cache.getOrDefault(key, 0) + 1);
        }
    }
}
