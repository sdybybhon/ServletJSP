package com.example.servlet;

import java.io.Serializable;
import java.nio.file.Paths;

public class User {
    private final String username;
    private final String password;
    private final String email;
    private final String homeDirectory;

    public User(String username, String password, String email, String homeDirectory) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.homeDirectory = homeDirectory;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getHomeDirectory() { return homeDirectory; }
}

