package com.example.servlet;

public class FileItem {
    private String name;
    private boolean directory;

    public FileItem(String name, boolean directory) {
        this.name = name;
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }
}