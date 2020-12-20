package com.pmihaylov._3_delegating_thread_safety;

import java.util.HashMap;
import java.util.Map;

public class People1 {
    private Map<String, Person> people = new HashMap<>();

    public synchronized Person get(String id) {
        return people.get(id);
    }

    public synchronized void set(String id, Person person) {
        people.put(id, person);
    }

    private static class Person {}
}
