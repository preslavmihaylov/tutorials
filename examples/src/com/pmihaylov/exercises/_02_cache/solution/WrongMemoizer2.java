package com.pmihaylov.exercises._02_cache.solution;

import com.pmihaylov.exercises._02_cache.Computable;

import java.util.concurrent.*;

/**
 * The problem with this memoizer is that atomic compound actions are not synchronized together.
 *
 * While a thread is calculating "next.compute(arg)", before executing "cache.put(arg, result)",
 * another one can enter & start calculating the value again.
 */
public class WrongMemoizer2<K, V> implements Computable<K, V> {
    private final ConcurrentMap<K, V> cache = new ConcurrentHashMap<>();
    private final Computable<K, V> next;

    public WrongMemoizer2(Computable<K, V> next) { this.next = next; }

    public V compute(final K arg) {
        V result = cache.get(arg);
        if (result == null) {
            result = next.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }
}