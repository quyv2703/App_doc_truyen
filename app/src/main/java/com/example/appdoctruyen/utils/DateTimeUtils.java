package com.example.appdoctruyen.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {
    // class này giúp chuyen ve dang 2 ngay trước

    private static final SimpleDateFormat isoFormat;

    static {
        isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private DateTimeUtils() {
        // Private constructor to prevent instantiation
    }

    public static String getTimeAgo(String isoDateString) {
        try {
            Date date = isoFormat.parse(isoDateString);
            Date now = new Date();
            long differenceInMillis = now.getTime() - date.getTime();

            long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            if (daysDifference > 0) {
                return daysDifference + " ngày trước";
            } else {
                long hoursDifference = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
                if (hoursDifference > 0) {
                    return hoursDifference + " giờ trước";
                } else {
                    long minutesDifference = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
                    if (minutesDifference > 0) {
                        return minutesDifference + " phút trước";
                    } else {
                        return "Vừa xong";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Không thể phân tích thời gian";
        }
    }
}