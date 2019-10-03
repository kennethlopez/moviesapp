package com.movies.ui.track.details;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movies.R;
import com.movies.data.remote.response.Track;
import com.movies.ui.base.BaseActivity;
import com.movies.util.AppUtil;
import com.movies.util.MediaPlayerUtil;


import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.movies.util.Constants.AppConstants.KEY_TRACK_JSON;
import static com.movies.util.Constants.AppConstants.TRACK_IMAGE_CORNER_RADIUS;

public class TrackDetailsActivity extends BaseActivity implements TrackDetailsContract.View {
    @Inject TrackDetailsPresenter mPresenter;
    @Inject MediaPlayerUtil mMediaPlayerUtil;

    public static Bundle createExtras(Track track) {
        Bundle args = new Bundle();
        args.putString(KEY_TRACK_JSON, AppUtil.getGson().toJson(track));

        return args;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        getComponent().inject(this);
        ButterKnife.bind(this);

        mPresenter.attachView(this);

        super.attachPresenter(mPresenter);
    }

    @Override
    public void initViews() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            String json = args.getString(KEY_TRACK_JSON);
            Track track = AppUtil.getGson().fromJson(json, Track.class);

            mPresenter.setTrack(track);
            mPlayFab.setOnClickListener(v -> mPresenter.onClickPlayFab());
            mVideoImage.setOnClickListener(v -> mPresenter.onClickPlayFab());
        }
    }

    @Override
    public void setPreviewImage(Bitmap bitmap) {
        AppUtil.setImage(this, bitmap, mVideoImage);
    }

    @Override
    public void setTrackImage(String url) {
        AppUtil.setImage(this, url, mTrackImage, TRACK_IMAGE_CORNER_RADIUS);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void setDate(String date) {
        mDate.setText(date);
    }

    @BindView(R.id.activity_track_details_video_img) AppCompatImageView mVideoImage;
    @BindView(R.id.activity_track_details_play_fab) FloatingActionButton mPlayFab;
    @BindView(R.id.activity_track_details_track_img) AppCompatImageView mTrackImage;
    @BindView(R.id.activity_track_details_title) AppCompatTextView mTitle;
    @BindView(R.id.activity_track_details_release_date) AppCompatTextView mDate;
    @BindView(R.id.activity_track_details_description) AppCompatTextView mDescription;
}
