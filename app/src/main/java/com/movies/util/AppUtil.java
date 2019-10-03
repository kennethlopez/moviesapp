package com.movies.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.movies.injection.module.GlideApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Utilities for the whole application
 * */
public class AppUtil {
    /**
     * A {@link Gson} which is built to exclude fields without {@link Expose} annotation
     * during serialization and deserialization
     * */
    private static final Gson sGson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    /**
     * Convert dp to px
     * @param dp The dp value
     * @return The converted value of dp into px
     * */
    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Puts the image link into the {@link ImageView} using Glide
     * @param context Application context
     * @param url Image link
     * @param imageView The ImageView where the image will be put
     * @param cornerDp corner radius in dp
     * */
    public static void setImage(Context context, String url, ImageView imageView, int cornerDp) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new RoundedCorners(dpToPx(cornerDp)));

        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(options)
                .into(imageView);
    }

    /**
     * Puts the Bitmap into the {@link ImageView} using Glide
     * @param context Application context
     * @param bitmap Image Bitmap
     * @param imageView The ImageView where the image will be put
     * */
    public static void setImage(Context context, Bitmap bitmap, ImageView imageView) {
        GlideApp.with(context)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * Returns the currency symbol based on the currency code
     * @param country The country code
     * @param currencyCode The currency code
     * @return The currency symbol based on the currency code
     * */
    public static String getCurrencySymbol(String country, String currencyCode) {
        Locale locale = new Locale("en", country);
        Currency currency = Currency.getInstance(currencyCode);

        return currency.getSymbol(locale);
    }

    /**
     * @return Returns the {@link #sGson}
     * */
    public static Gson getGson() {
        return sGson;
    }

    /**
     * Formats the specified date based on the input and output patterns
     * @param inputPattern The date pattern of the dateString
     * @param outputPattern The date pattern which will be used to format the output
     * @param dateString The date that needs to be formatted
     * @return The formatted date string based on the outputPattern
     * */
    public static String formatDate(String inputPattern, String outputPattern, String dateString)
            throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
        Date date = inputFormat.parse(dateString);
        if (date != null) {
            return outputFormat.format(date);
        }
        return null;
    }

    /**
     * Sets the visibility of the {@link View}
     * @param view The view
     * @param show Flag used to set the View's visibility
     * */
    public static void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
