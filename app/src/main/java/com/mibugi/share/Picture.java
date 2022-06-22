package com.mibugi.share;

import android.graphics.Bitmap;

/**
 * 图片实体类
 */
public class Picture {
    private String title;
    private String content;
    private Bitmap image;
    private String username;
    private Integer loveCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String sharerId) {
        this.username = username;
    }

    public Integer getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(Integer loveCount) {
        this.loveCount = loveCount;
    }
}
