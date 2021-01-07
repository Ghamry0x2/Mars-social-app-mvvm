package com.example.mars.utils;

import android.util.Log;

import static com.example.mars.utils.Constants.*;

public class Helpers {
    private static int SECOND_MILLIS = 1000;
    private static int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getTimeAgo(Long time)  {
        Long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "";
        }

        Long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return  (diff / MINUTE_MILLIS) + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return (diff / HOUR_MILLIS) + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return (diff / DAY_MILLIS) + " days ago";
        }
    }

    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }
}
