package com.pmihaylov._0_chatapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatApp {
    private static HashMap<String, List<String>> usersToMessages = new HashMap<>();

    public List<String> getMessages(String user) {
        return usersToMessages.get(user);
    }

    public void sendMessage(String user, String message) {
        usersToMessages.putIfAbsent(user, new ArrayList<>());
        usersToMessages.get(user).add(message);
    }
}
