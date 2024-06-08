package com.example.appdoctruyen.Module.output;

import com.example.appdoctruyen.Module.Story;
import com.example.appdoctruyen.Module.TheLoai;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseStoryGenreDetail {
    @SerializedName("data")
    private List<DataItem> data;
    @SerializedName("error")
    private String error;

    public List<DataItem> getData() {
        return data;
    }

    public String getError() {
        return error;
    }


    public static class DataItem{
        @SerializedName("story")
        private Story story;

        @SerializedName("genre")
        private TheLoai genre;
        public Story getStory() {
            return story;
        }
        public TheLoai getGenre() {
            return genre;
        }
    }

}
