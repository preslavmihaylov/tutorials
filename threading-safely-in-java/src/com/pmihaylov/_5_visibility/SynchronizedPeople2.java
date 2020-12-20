package com.pmihaylov._5_visibility;

import java.util.HashMap;
import java.util.Map;

public class SynchronizedPeople2 {
    private Map<String, Person> people = new HashMap<>();

    public synchronized Person get(String id) {
        return people.get(id);
    }

    public synchronized void set(String id, Person person) {
        people.put(id, person);
    }

    private static class Person {}
}
