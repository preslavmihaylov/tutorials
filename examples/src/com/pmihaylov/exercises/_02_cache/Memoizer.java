package com.pmihaylov.exercises._02_cache;

public class Memoizer<K, V> implements Computable<K, V> {
    // Assignment:
    //
    // Memoizer should cache the values computed via "next.compute(arg)".
    //
    // When someone invokes "compute":
    //  * If this is the first time the func is invoked with "arg", calculate the result via "next.compute(arg).
    //  * If not, return the value which is already cached.
    //
    // If a request for a given arg comes while it's being calculated,
    // the request should wait for the result instead of calculating it via "next.compute(arg)" again.
    //
    // Add/modify fields as necessary.

    private final Computable<K, V> next;

    public Memoizer(Computable<K, V> c) { this.next = c; }

    public V compute(final K arg) {
        throw new UnsupportedOperationException("unimplemented");
    }
}