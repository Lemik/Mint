package com.example.leoniddushin.mint2.Objects;

/**
 * Created by leoniddushin on 15-08-16.
 */
public class Search {
    private int imageName;
    private String title;
    private String Desc;

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDesc() {
        return Desc;
    }

    public Search(int imageName, String title, String desc) {
        this.imageName = imageName;
        this.title = title;
        this.Desc = desc;
    }

    public int getImageName() {
        return imageName;
    }

    public void setImageName(int imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
