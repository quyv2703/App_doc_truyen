package com.example.appdoctruyen.Module;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Chapter implements Parcelable {
   private int id;
    private int order;
    private String name;
    private int status;
    private String createdAt;
    private  String updatedAt;
    private int storyId;

    protected Chapter(Parcel in) {
        id = in.readInt();
        order = in.readInt();
        name = in.readString();
        status = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        storyId = in.readInt();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public int getId() {
        return id;
    }



    public int getOrder() {
        return order;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(order);
        parcel.writeString(name);
        parcel.writeInt(status);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeInt(storyId);
    }
}
