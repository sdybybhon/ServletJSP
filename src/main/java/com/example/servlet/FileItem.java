package com.example.servlet;

import java.text.SimpleDateFormat;
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

    public String getFormattedCreationDate() {
        if (creationDate == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(creationDate);
    }
}