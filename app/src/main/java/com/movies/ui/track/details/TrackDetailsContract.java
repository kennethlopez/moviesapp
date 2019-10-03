package com.movies.ui.track.details;

import android.graphics.Bitmap;

import com.movies.data.remote.response.Track;
import com.movies.ui.base.BaseView;

/**
 * Presenter and View interfaces for {@link TrackDetailsActivity} and {@link TrackDetailsPresenter}
 * */
interface TrackDetailsContract {
    interface View extends BaseView {

        void setPreviewImage(Bitmap bitmap);

        void setTrackImage(String url);

        void setTitle(String title);

        void setDescription(String description);

        void setDate(String date);

        void gotoVideoPlayer(String videoUrl);
    }

    interface Presenter {
        void setTrack(Track track);

        void onClickPlayFab();
    }
}
