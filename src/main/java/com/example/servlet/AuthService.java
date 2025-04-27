package com.example.servlet;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static final Map<String, User> users = new HashMap<>();
    private static final String BASE_DIR = "C:/Student/filemanager";

    static {
        users.put("admin", new User("admin", "admin123",
                "admin@example.com", BASE_DIR));
    }

    public static User authenticate(String username, String password) {
        User user = users.get(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    public static void register(User user) {
        users.put(user.getUsername(), user);
    }
}