package com.movies.ui.home;

import android.content.Context;
import android.view.View;

import com.movies.R;
import com.movies.util.AppUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.movies.util.Constants.AppConstants.TRACK_IMAGE_CORNER_RADIUS;

/**
 * ViewHolder for {@link TracksAdapter}
 * */
class TrackViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    TrackViewHolder(Context context, @NonNull View itemView) {
        super(itemView);

        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    /**
     * Sets the image
     * @param url Link to the image
     * */
    void setImage(String url) {
        AppUtil.setImage(mContext, url, mThumbnail, TRACK_IMAGE_CORNER_RADIUS);
    }

    /**
     * Sets the title
     * @param title The title
     * */
    void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * Sets the price
     * @param price The price
     * */
    void setPrice(String price) {
        mPrice.setText(price);
    }

    @BindView(R.id.item_track_img) AppCompatImageView mThumbnail;
    @BindView(R.id.item_track_title) AppCompatTextView mTitle;
    @BindView(R.id.item_track_price) AppCompatTextView mPrice;
}
