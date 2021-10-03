package com.pmihaylov.synchronization;

import java.util.concurrent.atomic.AtomicLong;

import static com.pmihaylov.Common.parallelize;

public class _02_counterTests {
    public static void main(String[] args) {
        System.out.println("HitCounter test.");

        System.out.println("\nUnsyncHitCounter example (hit 10000 times):");
        UnsyncHitCounter unsyncCnt = new UnsyncHitCounter();
        parallelize(10000, unsyncCnt::hit);

        // probably won't be 10000! (not thread-safe)
        System.out.println(unsyncCnt.getHits());

        System.out.println("\nSyncHitCounter example (hit 10000 times):");
        SyncHitCounter syncCnt = new SyncHitCounter();
        parallelize(10000, syncCnt::hit);

        // should be 10000 (thread-safe)
        System.out.println(syncCnt.getHits());

        System.out.println("\nAtomicHitCounter example (hit 10000 times):");
        AtomicHitCounter atomicCnt = new AtomicHitCounter();
        parallelize(10000, atomicCnt::hit);

        // should be 10000 (thread-safe)
        System.out.println(atomicCnt.getHits());
    }

    static class UnsyncHitCounter {
        private long hits;

        public long getHits() {
            return hits;
        }

        public void hit() {
            ++hits;
        }
    }

    static class SyncHitCounter {
        private long hits;

        public synchronized long getHits() {
            return hits;
        }

        public synchronized void hit() {
            ++hits;
        }
    }

    static class AtomicHitCounter {
        private AtomicLong hits = new AtomicLong(0);

        public long getHits() {
            return hits.get();
        }

        public void hit() {
            hits.getAndIncrement();
        }
    }
}
