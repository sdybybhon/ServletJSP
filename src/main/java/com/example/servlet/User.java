package com.example.servlet;

import java.io.Serializable;
import java.nio.file.Paths;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private final String email;
    private final String homeDirectory;

    public User(String username, String password, String email, String homeBasePath) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.homeDirectory = Paths.get(homeBasePath, username).toString();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getHomeDirectory() { return homeDirectory; }
}