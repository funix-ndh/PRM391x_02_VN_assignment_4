package com.example.shopmovies;

public class UserInfo {
    private String name;
    private String email;
    private String image;
    private String birthday;

    public UserInfo() {
    }

    public UserInfo(String name, String email, String image, String birthday) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}