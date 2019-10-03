package com.movies.util;

public interface Constants {

    interface AppConstants {
        String API_SERVICE_HOST = "https://itunes.apple.com/";
        String APP_NAME = "Movies";
        String KEY_TRACK_JSON = "KEY_TRACK_JSON";
        int TRACK_IMAGE_CORNER_RADIUS = AppUtil.dpToPx(3);
        String RESPONSE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:SS'Z'";
        String DISPLAY_DATE_PATTERN = "MMMM yyyy";
    }
}
