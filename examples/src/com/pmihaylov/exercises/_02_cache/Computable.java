package com.pmihaylov.exercises._02_cache;

public interface Computable<K, V> {
    V compute(K arg);
}
