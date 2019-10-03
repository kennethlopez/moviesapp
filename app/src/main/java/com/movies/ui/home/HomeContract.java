package com.movies.ui.home;

import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.Track;
import com.movies.ui.base.BaseView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

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

    interface Presenter {
        void onRefresh();

        void onClickRetry();
    }
}
