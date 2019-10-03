package com.movies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Utilities for network management
 * */
public class NetworkUtil {

    /**
     * Indicates whether device is connected to Wifi/data
     * @param context Application context
     * @return {@code true} if device is connected to Wifi/data, {@code false} otherwise.
     * */
    public static boolean isNetworkConnected(final Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        return false;
    }

    /**
     * Indicates whether device has access to the internet
     * @param throwable The throwable which needs to checked
     * @return {@code true} if device has access to the internet, {@code false} otherwise.
     * */
    public static boolean isConnectionError(Throwable throwable) {
        return throwable instanceof UnknownHostException ||
                throwable instanceof SocketTimeoutException ||
                throwable instanceof ConnectException;
    }
}
