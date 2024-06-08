package com.example.appdoctruyen.Module.output;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("refreshToken")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
// Thêm các trường dữ liệu khác tùy theo API của bạn
