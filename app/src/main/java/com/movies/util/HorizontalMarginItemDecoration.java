package com.movies.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalMarginItemDecoration extends RecyclerView.ItemDecoration {
    private final int mHorizontalMargin;

    public HorizontalMarginItemDecoration(int margin) {
        mHorizontalMargin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        outRect.left = mHorizontalMargin;
        outRect.right = mHorizontalMargin;
    }
}
