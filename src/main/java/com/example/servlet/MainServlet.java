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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathParam = req.getParameter("path");
        Path currentPath;

        if (pathParam == null || pathParam.isEmpty()) {
            currentPath = Paths.get("").toAbsolutePath();
        } else {
            currentPath = Paths.get(pathParam);
        }

        if (!Files.isDirectory(currentPath)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid directory path");
            return;
        }

        List<FileItem> items;
        try {
            items = Files.list(currentPath)
                    .sorted()
                    .map(path -> new FileItem(
                            path.getFileName().toString(),
                            Files.isDirectory(path)
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to list directory contents");
            return;
        }

        Path parentPath = currentPath.getParent();
        req.setAttribute("currentPath", currentPath.toString());
        req.setAttribute("parentPath", parentPath != null ? parentPath.toString() : null);
        req.setAttribute("items", items);
        req.setAttribute("generatedTime", new java.util.Date());

        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }
}