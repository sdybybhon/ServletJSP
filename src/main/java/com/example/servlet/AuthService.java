package com.example.servlet;

import jakarta.persistence.*;

import java.io.IOException;
import java.nio.file.*;

public class AuthService {
    private static final String BASE_DIR = "C:/Student/filemanager";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("FileManager");
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static User authenticate(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
                    User.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
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

        user.setHomeDirectory(userHomeDirectory);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Registration failed", e);
        } finally {
            em.close();
        }
    }
}