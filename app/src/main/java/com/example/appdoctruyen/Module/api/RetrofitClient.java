package com.example.appdoctruyen.Module.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitWithAuth = null;
    private static Retrofit retrofitWithoutAuth = null;
    public static Retrofit retrofit;
    public static final String BASE_URL = "http://192.168.2.11:8080/api/v1/";

    public static Retrofit getRetrofitInstanceWithAuth(String token) {
        if (retrofitWithAuth == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(token))
                    .build();

            retrofitWithAuth = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithAuth;
    }

    public static Retrofit getRetrofitInstanceWithoutAuth() {
        if (retrofitWithoutAuth == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            retrofitWithoutAuth = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithoutAuth;
    }
}

