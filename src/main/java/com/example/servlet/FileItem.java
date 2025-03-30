package com.example.servlet;

import java.nio.file.attribute.FileTime;
import java.util.Date;

public class FileItem {
    private String name;
    private boolean directory;
    private long size;
    private Date creationDate;

    public FileItem(String name, boolean directory, long size, Date creationDate) {
        this.name = name;
        this.directory = directory;
        this.size = size;
        this.creationDate = creationDate;
    }

    public String getName() { return name; }
    public boolean isDirectory() { return directory; }
    public long getSize() { return size; }
    public Date getCreationDate() { return creationDate; }
}