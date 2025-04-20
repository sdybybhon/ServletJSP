package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String filePath = req.getParameter("file");
        if (filePath == null || filePath.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File parameter is missing");
            return;
        }

        Path path = Paths.get(filePath).toAbsolutePath().normalize();

        if (!Files.exists(path) || Files.isDirectory(path)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        String fileName = path.getFileName().toString();
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replace("+", "%20");

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition",
                "attachment; filename*=UTF-8''" + encodedFileName);
        resp.setContentLengthLong(Files.size(path));

        try {
            Files.copy(path, resp.getOutputStream());
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error downloading file");
        }
    }
}