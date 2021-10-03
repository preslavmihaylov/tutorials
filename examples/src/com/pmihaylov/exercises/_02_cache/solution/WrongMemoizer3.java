package com.pmihaylov.exercises._02_cache.solution;

import com.pmihaylov.exercises._02_cache.Computable;

import java.util.Map;
import java.util.concurrent.*;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

/**
 * This memoizer is much more synchronized than the previous version but there is still a small window of opportunity for calculating a value twice.
 *
 * Between "cache.get(arg)" and "cache.put(arg, f)",
 * a second thread can enter and see that a future is not set in the cache yet and start calculating it.
 */
public class WrongMemoizer3<K, V> implements Computable<K, V> {
    private final Map<K, FutureTask<V>> cache = new ConcurrentHashMap<>();
    private final Computable<K, V> next;

    public WrongMemoizer3(Computable<K, V> next) { this.next = next; }

    public V compute(final K arg) {
        FutureTask<V> f = cache.get(arg);
        if (f == null) {
            f = new FutureTask<>(() -> next.compute(arg));
            cache.put(arg, f);
            f.run(); // call to c.compute happens here
        }

        return ignoreCheckedExceptions((Callable<V>) f::get);
    }
}
