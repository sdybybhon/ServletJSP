package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Path homePath = Paths.get(user.getHomeDirectory()).toAbsolutePath().normalize();

        try {
            if (!Files.exists(homePath)) {
                Files.createDirectories(homePath);
            }
        } catch (IOException e) {
            resp.sendError(500, "Ошибка создания домашней директории");
            return;
        }

        String pathParam = req.getParameter("path");
        Path currentPath;

        if (pathParam == null || pathParam.isEmpty()) {
            currentPath = homePath;
        } else {
            try {
                String decodedPath = URLDecoder.decode(pathParam, StandardCharsets.UTF_8);
                currentPath = homePath.resolve(decodedPath).toAbsolutePath().normalize();

                if (!currentPath.startsWith(homePath)) {
                    resp.sendError(403, "Доступ запрещён");
                    return;
                }
            } catch (IllegalArgumentException e) {
                resp.sendError(400, "Некорректный путь");
                return;
            }
        }

        if (!Files.isDirectory(currentPath)) {
            resp.sendError(400, "Указанный путь не является директорией");
            return;
        }

        List<FileItem> items;
        items = Files.list(currentPath)
                .sorted()
                .map(path -> {
                    long size = -1;
                    Date creationDate = null;

                    try {
                        size = Files.isDirectory(path) ? -1 : Files.size(path);
                        FileTime creationTime = (FileTime) Files.getAttribute(path, "creationTime");
                        if (creationTime != null) {
                            creationDate = new Date(creationTime.toMillis());
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка для файла: " + path + " - " + e.getMessage());
                    }

                    return new FileItem(
                            path.getFileName().toString(),
                            Files.isDirectory(path),
                            size,
                            creationDate
                    );
                })
                .collect(Collectors.toList());

        Path parentPath = currentPath.getParent();
        boolean isHomeDirectory = homePath.equals(currentPath);
        String parentParam = null;

        if (!isHomeDirectory && parentPath != null && parentPath.startsWith(homePath)) {
            String relativeParentPath = homePath.relativize(parentPath).toString();
            parentParam = URLEncoder.encode(relativeParentPath, StandardCharsets.UTF_8);
        }

        String relativeCurrentPath = homePath.relativize(currentPath).toString();
        String encodedCurrentPath = URLEncoder.encode(relativeCurrentPath, StandardCharsets.UTF_8);
        String decodedCurrentPath = URLDecoder.decode(encodedCurrentPath, StandardCharsets.UTF_8);
        String encodedFilePath = URLEncoder.encode(decodedCurrentPath, StandardCharsets.UTF_8);

        req.setAttribute("currentPath", encodedCurrentPath);
        req.setAttribute("decodedCurrentPath", decodedCurrentPath);
        req.setAttribute("encodedCurrentPath", encodedFilePath);
        req.setAttribute("parentPath", parentParam);
        req.setAttribute("isHomeDirectory", isHomeDirectory);
        req.setAttribute("items", items);
        req.setAttribute("generatedTime", new Date());
        req.setAttribute("user", user);

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}