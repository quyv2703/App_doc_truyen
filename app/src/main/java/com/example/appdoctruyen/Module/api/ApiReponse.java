package com.example.appdoctruyen.Module.api;

import com.example.appdoctruyen.Module.Story;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiReponse<T> {
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public ApiReponse() {
    }

    public ApiReponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }
    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }


}
