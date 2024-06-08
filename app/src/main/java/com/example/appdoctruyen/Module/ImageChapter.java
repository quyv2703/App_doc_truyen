package com.example.appdoctruyen.Module;

public class ImageChapter {

    private int id;
     private String path;
     private int chapterId;
     private int order;

    public ImageChapter(int id, String path, int chapterId, int order) {
        this.id = id;
        this.path = path;
        this.chapterId = chapterId;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
