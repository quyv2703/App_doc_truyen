package com.example.appdoctruyen.Module.input;

import com.google.gson.annotations.SerializedName;

public class InputTheoDoi {
    @SerializedName("storyId")
    private int storyId;
    @SerializedName("userId")
    private int userId;

    public InputTheoDoi() {
    }

    public InputTheoDoi(int storyId, int userId) {
        this.storyId = storyId;
        this.userId = userId;
    }

    public int getStoryId() {
        return storyId;
    }



    public int getUserId() {
        return userId;
    }


}
