package com.example.servlet;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "home_directory", nullable = false, length = 255)
    private String homeDirectory;

    public User() {}

    public User(String username, String password, String email, String homeDirectory) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.homeDirectory = homeDirectory;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getHomeDirectory() { return homeDirectory; }
    public void setHomeDirectory(String homeDirectory) { this.homeDirectory = homeDirectory; }
}