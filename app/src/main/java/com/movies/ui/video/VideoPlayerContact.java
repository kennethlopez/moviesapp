package com.movies.ui.video;


import com.movies.ui.base.BasePresenter;
import com.movies.ui.base.BaseView;

/**
 * Presenter and View interfaces for {@link VideoPlayerActivity} and {@link VideoPlayerPresenter}
 * */
public interface VideoPlayerContact {
    interface View extends BaseView {

        void initializePlayer(String videoUrl);

        void pausePlayerView();

        void resumePlayerView();

        boolean playerInitialized();

        void releasePlayer();

    }

    interface Presenter extends BasePresenter<View> {

        void setVideoUrl(String videoUrl);
    }
}
