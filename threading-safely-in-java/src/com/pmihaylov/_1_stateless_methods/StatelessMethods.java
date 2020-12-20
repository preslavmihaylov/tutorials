package com.pmihaylov._1_stateless_methods;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatelessMethods {
    public Map<Integer, String> weekendsSafe() {
        Map<Integer, String> days = new ConcurrentHashMap<>();
        days.put(6, "Saturday");
        days.put(7, "Sunday");

        return days;
    }

    public Map<Integer, String> weekendsUnsafe() {
        Map<Integer, String> days = new HashMap<>();
        days.put(6, "Saturday");
        days.put(7, "Sunday");

        return days;
    }
}
