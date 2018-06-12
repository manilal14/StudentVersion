package com.example.mani.studentversion.NewsRelaled;

/**
 * Created by mani on 2/13/18.
 */

public class Feeds {
    int id;
    private String title,desc;
    String image_path;

    public Feeds(int id,String title, String desc,String image_path) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.image_path = image_path;
    }

    public int getId(){
        return id;
    }
    public String getImagePath() {
        return image_path;
    }
    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
