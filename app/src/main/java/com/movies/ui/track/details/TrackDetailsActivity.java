package com.movies.ui.track.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movies.R;
import com.movies.data.remote.response.Track;
import com.movies.injection.component.ActivityComponent;
import com.movies.ui.base.BaseActivity;
import com.movies.ui.video.VideoPlayerActivity;
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

/**
 * {@link TrackDetailsPresenter}'s View
 * */
public class TrackDetailsActivity extends BaseActivity<TrackDetailsContract.View> implements
        TrackDetailsContract.View {
    @Inject TrackDetailsPresenter mPresenter;
    @Inject MediaPlayerUtil mMediaPlayerUtil;

    /**
     * Creates a {@link Bundle} with extras
     * @param track The Track that would be serialized and put on as a Bundle extra
     * @return A new Bundle
     * */
    public static Bundle createExtras(Track track) {
        Bundle args = new Bundle();
        args.putString(KEY_TRACK_JSON, AppUtil.getGson().toJson(track));

        return args;
    }

    /**
     * Injects the class to the {@link ActivityComponent}.
     * Binds the class to the ButterKnife.
     * Attaches the Presenter to the BaseActivity
     * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        getComponent().inject(this);
        ButterKnife.bind(this);

        super.attachPresenter(mPresenter, this);
    }

    /**
     * Initializes the Views
     * */
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

    /**
     * Sets the track's preview video image
     * @param bitmap The Bitmap image
     * */
    @Override
    public void setPreviewImage(Bitmap bitmap) {
        AppUtil.setImage(this, bitmap, mVideoImage);
    }

    /**
     * Sets the track's image
     * @param url Link to the image
     * */
    @Override
    public void setTrackImage(String url) {
        AppUtil.setImage(this, url, mTrackImage, TRACK_IMAGE_CORNER_RADIUS);
    }

    /**
     * Sets the track's title
     * @param title The track title
     * */
    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * Sets the track's description
     * @param description The track description
     * */
    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    /**
     * Sets the track's release date
     * */
    @Override
    public void setDate(String date) {
        mDate.setText(date);
    }

    /**
     * Opens the {@link VideoPlayerActivity}
     * @param videoUrl Link of the media file to be played
     * */
    @Override
    public void gotoVideoPlayer(String videoUrl) {
        Bundle args = VideoPlayerActivity.createExtras(videoUrl);
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }

    @BindView(R.id.activity_track_details_video_img) AppCompatImageView mVideoImage;
    @BindView(R.id.activity_track_details_play_fab) FloatingActionButton mPlayFab;
    @BindView(R.id.activity_track_details_track_img) AppCompatImageView mTrackImage;
    @BindView(R.id.activity_track_details_title) AppCompatTextView mTitle;
    @BindView(R.id.activity_track_details_release_date) AppCompatTextView mDate;
    @BindView(R.id.activity_track_details_description) AppCompatTextView mDescription;
}
