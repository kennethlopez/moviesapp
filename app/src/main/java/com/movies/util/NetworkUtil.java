package com.movies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class NetworkUtil {

    /**
     * Returns true if device is connected to Wifi/data
     * */
    public static boolean isNetworkConnected(final Context context) {
        // check if connected to WiFi/data
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        return false;
    }

    /**
     * returns true if exception was caused by device not able to connect to internet
     * */
    public static boolean isConnectionError(Throwable throwable) {
        return throwable instanceof UnknownHostException ||
                throwable instanceof SocketTimeoutException ||
                throwable instanceof ConnectException;
    }
}
