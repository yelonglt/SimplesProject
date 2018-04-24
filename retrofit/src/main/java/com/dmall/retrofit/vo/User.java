package com.dmall.retrofit.vo;

/**
 * 用户信息
 * Created by eyetech on 16/5/20.
 * mail:354734713@qq.com
 */
public class User {

    private final int userId;
    private String username;
    private int age;
    private String address;
    private String image;

    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
