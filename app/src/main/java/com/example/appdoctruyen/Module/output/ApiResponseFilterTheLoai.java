package com.example.appdoctruyen.Module.output;

import com.example.appdoctruyen.Module.TheLoai;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseFilterTheLoai {
    @SerializedName("data")
    private List<TheLoai> data;
    @SerializedName("error")
    private String error;

    public List<TheLoai> getData() {
        return data;
    }

    public void setData(List<TheLoai> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
