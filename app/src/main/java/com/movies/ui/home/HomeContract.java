package com.movies.ui.home;

import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.Track;
import com.movies.ui.base.BasePresenter;
import com.movies.ui.base.BaseView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Presenter and View interfaces for {@link HomeActivity} and {@link HomePresenter}
 * */
public interface HomeContract {
    interface View extends BaseView {
        void setRefreshing(boolean refreshing);

        void displayTracksGroups(List<TrackGroup> trackGroups);

        void showNoInternetMessage(boolean show);

        void showRetryBtn(boolean show);

        void showRetryProgressBar(boolean show);

        void enableSwipeRefresh(boolean enable);

        void showNoNetworkConnectionMessage(boolean show);

        PublishSubject<Track> onTrackClicked();

        void gotoTrackDetails(Track track);
    }

    interface Presenter extends BasePresenter<View> {
        void onRefresh();

        void onClickRetry();
    }
}
