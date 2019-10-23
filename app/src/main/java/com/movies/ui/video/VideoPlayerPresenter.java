package com.movies.ui.video;

import com.google.android.exoplayer2.util.Util;
import com.movies.injection.ConfigPersistent;
import com.movies.ui.base.Presenter;

import javax.inject.Inject;


/**
 * {@link VideoPlayerActivity}'s Presenter
 * */
@ConfigPersistent
public class VideoPlayerPresenter extends Presenter<VideoPlayerContact.View> implements
    VideoPlayerContact.Presenter {
    private String mVideoUrl;

    @Inject
    VideoPlayerPresenter() { }

    /**
     * Sets {@link #mVideoUrl}
     * */
    @Override
    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    // Note: "Util.SDK_INT" if conditions prevents the execution of the same code on onStart and onResume
    // and on onPause and onStop

    /**
     * Resume/Start the video player
     * */
    @Override
    protected void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initializePlayer();
            getView().resumePlayerView();
        }
    }

    /**
     * Resume/Start the media player
     * */
    @Override
    protected void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23 || !getView().playerInitialized()) {
            initializePlayer();
            getView().resumePlayerView();
        }
    }

    /**
     * Pause and release the media player
     * */
    @Override
    protected void onPause() {
        super.onPause();

        if (Util.SDK_INT <= 23) {
            getView().pausePlayerView();
            getView().releasePlayer();
        }
    }

    /**
     * Pause and release the media player
     * */
    @Override
    protected void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            getView().pausePlayerView();
            getView().releasePlayer();
        }
    }

    /**
     * Initialize the media player with {@link #mVideoUrl}
     * */
    private void initializePlayer() {
        if (!getView().playerInitialized()) {
            getView().initializePlayer(mVideoUrl);
        }
    }
}
