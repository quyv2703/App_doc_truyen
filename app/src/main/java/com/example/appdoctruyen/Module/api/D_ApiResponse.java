package com.example.appdoctruyen.Module.api;

import com.example.appdoctruyen.Module.Story;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class D_ApiResponse {
    @SerializedName("data")
    private Data data;


    @SerializedName("error")
    private String error;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class Data {
        private List<Story> stories;
        private int count;

        public List<Story> getStories() {
            return stories;
        }

        public void setStories(List<Story> stories) {
            this.stories = stories;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
