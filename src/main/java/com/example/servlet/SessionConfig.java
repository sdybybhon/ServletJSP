package com.example.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SessionConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        SessionCookieConfig sessionCookieConfig = context.getSessionCookieConfig();
        sessionCookieConfig.setName("JSESSIONID");
        sessionCookieConfig.setPath("/");
        sessionCookieConfig.setHttpOnly(true);
        sessionCookieConfig.setMaxAge(86400);

        context.setSessionTimeout(60);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}