package com.example.appdoctruyen.Module.input;

import com.google.gson.annotations.SerializedName;

public class InputDangKi {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("photoURL")
    private String photoURL="https://winaero.com/blog/wp-content/uploads/2018/08/Windows-10-user-icon-big.png";

    public InputDangKi(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }
}
