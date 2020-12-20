package com.pmihaylov._3_delegating_thread_safety;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class People2 {
    private Map<String, Person> people = new ConcurrentHashMap<>();

    public Person get(String id) {
        return people.get(id);
    }

    public void set(String id, Person person) {
        people.put(id, person);
    }

    private static class Person {}
}
