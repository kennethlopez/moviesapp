package com.movies.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Provides horizontal margin for RecyclerView items
 * */
public class HorizontalMarginItemDecoration extends RecyclerView.ItemDecoration {
    private final int mHorizontalMargin;

    public HorizontalMarginItemDecoration(int margin) {
        mHorizontalMargin = margin;
    }

    /**
     * Sets the left and right margin of the RecyclerView items
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     * */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        outRect.left = mHorizontalMargin;
        outRect.right = mHorizontalMargin;
    }
}
