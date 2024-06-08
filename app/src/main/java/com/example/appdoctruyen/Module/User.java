package com.example.appdoctruyen.Module;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("photoURL")
    private String photoURL;
    @SerializedName("email")
    private String email;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String displayName, String photoURL, String email) {
        this.id = id;
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.email = email;
    }
}
