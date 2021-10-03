package com.pmihaylov.exercises._02_cache;

// Uncomment this line to see how the solution should work...
// import com.pmihaylov.exercises._02_cache.solution.Memoizer;

import com.pmihaylov.exercises._02_cache.solution.WrongMemoizer1;
import com.pmihaylov.exercises._02_cache.solution.WrongMemoizer2;
import com.pmihaylov.exercises._02_cache.solution.WrongMemoizer3;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pmihaylov.Common.parallelize;

public class Main {
    public static void main(String[] args) {
        MockComputable mock = new MockComputable();
        testMemoizer(new WrongMemoizer1<>(mock), mock.invocations);

        mock = new MockComputable();
        testMemoizer(new WrongMemoizer2<>(mock), mock.invocations);

        mock = new MockComputable();
        testMemoizer(new WrongMemoizer3<>(mock), mock.invocations);

        mock = new MockComputable();
        testMemoizer(new Memoizer<>(mock), mock.invocations);
    }

    public static void testMemoizer(Computable<Integer, Integer> comp, ConcurrentMap<Integer, Integer> results) {
        AtomicInteger cnt1 = new AtomicInteger(0);
        AtomicInteger cnt2 = new AtomicInteger(0);
        AtomicInteger cnt3 = new AtomicInteger(0);
        parallelize(10000,
                () -> comp.compute(cnt1.incrementAndGet()),
                () -> comp.compute(cnt2.incrementAndGet()),
                () -> comp.compute(cnt3.incrementAndGet()));

        int violationsCnt = 0;
        for (int i = 1; i <= cnt1.get(); i++) {
            int actual = results.get(i);
            if (actual != 1) {
                violationsCnt++;
            }
        }

        if (violationsCnt == 0) {
            System.out.printf("%s is thread-safe!%s\n",
                    comp.getClass().getSimpleName(),
                    comp.getClass().getSimpleName().equals("WrongMemoizer1") ? " (but very slow...)" : "");
        } else {
            System.out.printf("%s is NOT thread-safe with %d violations!\n", comp.getClass().getSimpleName(), violationsCnt);
        }
    }

    static class MockComputable implements Computable<Integer, Integer> {
        ConcurrentMap<Integer, Integer> invocations = new ConcurrentHashMap<>();

        @Override
        public Integer compute(Integer arg) {
            invocations.put(arg, invocations.getOrDefault(arg, 0) + 1);
            return invocations.get(arg);
        }
    }
}
