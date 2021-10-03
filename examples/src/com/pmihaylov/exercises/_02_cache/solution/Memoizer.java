package com.pmihaylov.exercises._02_cache.solution;

import com.pmihaylov.exercises._02_cache.Computable;

import java.util.concurrent.*;

import static com.pmihaylov.Common.ignoreCheckedExceptions;

public class Memoizer<K, V> implements Computable<K, V> {
    private final ConcurrentMap<K, FutureTask<V>> cache = new ConcurrentHashMap<>();
    private final Computable<K, V> next;

    public Memoizer(Computable<K, V> next) { this.next = next; }

    public V compute(final K arg) {
        FutureTask<V> ft = new FutureTask<>(() -> next.compute(arg));
        if (cache.putIfAbsent(arg, ft) == null) {
            ft.run();
        }

        return ignoreCheckedExceptions(() -> cache.get(arg).get());
    }
}
