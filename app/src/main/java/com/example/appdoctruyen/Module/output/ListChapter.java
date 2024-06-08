package com.example.appdoctruyen.Module.output;

import com.example.appdoctruyen.Module.Chapter;

import java.io.Serializable;
import java.util.List;

public class ListChapter {
    private Data data;
    private String error;

    public Data getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public static class Data  {
        private List<Chapter> chapters;
        private int count;

        public List<Chapter> getChapters() {
            return chapters;
        }

        public int getCount() {
            return count;
        }
    }


}
