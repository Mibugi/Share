package com.mibugi.share;

import android.graphics.Bitmap;

/**
 * 用户实体类
 */
public class User {
    String username;

    public Bitmap getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(Bitmap touxiang) {
        this.touxiang = touxiang;
    }

    Bitmap touxiang;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;
    public User(String username, String password){
        this.password=password;
        this.username=username;
    }
}
