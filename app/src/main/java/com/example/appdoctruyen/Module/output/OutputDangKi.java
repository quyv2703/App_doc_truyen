package com.example.appdoctruyen.Module.output;

import com.google.gson.annotations.SerializedName;

public class OutputDangKi {
    @SerializedName("email")
    private String email;
    @SerializedName("id")
    private int id;

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
