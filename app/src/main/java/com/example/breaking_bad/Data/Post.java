package com.example.breaking_bad.Data;

public class Post {
    private  String name;
    private String img;


    public Post(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
