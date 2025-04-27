package com.example.servlet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;


public class AuthService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/filemanager";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";
    private static final String BASE_DIR = "C:/Student/filemanager";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("home_directory")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
        return null;
    }

    public static void register(User user) {
        String userHomeDirectory = BASE_DIR + "/" + user.getUsername();

        try {
            Path homePath = Paths.get(userHomeDirectory);
            if (!Files.exists(homePath)) {
                Files.createDirectories(homePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create user directory", e);
        }

        String sql = "INSERT INTO users (username, password, email, home_directory) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, userHomeDirectory);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Registration failed", e);
        }
    }
}