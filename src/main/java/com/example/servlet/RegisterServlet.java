package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String BASE_DIR = "C:/Student/filemanager";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (AuthService.authenticate(username, password) != null) {
            request.setAttribute("error", "Пользователь уже существует");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User(username, password, email, BASE_DIR);
        Path userHome = Paths.get(newUser.getHomeDirectory());

        try {
            if (!userHome.startsWith(Paths.get(BASE_DIR))) {
                throw new IOException("Некорректный путь");
            }

            Files.createDirectories(userHome);

            AuthService.register(newUser);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            request.setAttribute("error", "Ошибка создания директории пользователя");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}