package com.pmihaylov.exercises._02_cache.solution;

import com.pmihaylov.exercises._02_cache.Computable;

import java.util.HashMap;
import java.util.Map;

/**
 * The problem with this memoizer is that its synchronization is overly conservative.
 * With this implementation, only a single value can be calculated at a time.
 */
public class WrongMemoizer1<K, V> implements Computable<K, V> {
    private final Map<K, V> cache = new HashMap<>();
    private final Computable<K, V> next;

    public WrongMemoizer1(Computable<K, V> next) { this.next = next; }

    public synchronized V compute(final K arg) {
        V result = cache.get(arg);
        if (result == null) {
            result = next.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }
}
