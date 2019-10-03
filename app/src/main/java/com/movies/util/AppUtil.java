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
import com.movies.injection.module.GlideApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class AppUtil {
    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

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

    public static void setImage(Context context, Bitmap bitmap, ImageView imageView) {
        GlideApp.with(context)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static String getCurrencySymbol(String country, String currencyCode) {
        Locale locale = new Locale("en", country);
        Currency currency = Currency.getInstance(currencyCode);

        return currency.getSymbol(locale);
    }

    public static Gson getGson() {
        return gson;
    }

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

    public static void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
