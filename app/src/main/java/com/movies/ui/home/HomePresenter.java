package com.movies.ui.home;

import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.SearchResult;
import com.movies.data.remote.response.Track;
import com.movies.data.repository.TrackRepository;
import com.movies.injection.ConfigPersistent;
import com.movies.ui.base.Presenter;
import com.movies.util.NetworkUtil;
import com.movies.util.RxUtil;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link HomeActivity}'s Presenter
 * */
@ConfigPersistent
public class HomePresenter extends Presenter<HomeContract.View> implements HomeContract.Presenter {
    private final TrackRepository mTrackRepository;

    private Disposable mDisposable;
    private Disposable mOnTrackClickedDisposable;

    @Inject
    HomePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    /**
     * Attaches the {@link HomeContract.View} to the {@link Presenter}
     * */
    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);

        getView().initViews();
        searchTracks();
        onTrackClicked();
    }

    /**
     * Shows the ProgressBar on SwipeRefreshLayout and invokes {@link #searchTracks()}
     * */
    @Override
    public void onRefresh() {
        getView().setRefreshing(true);
        searchTracks();
    }

    /**
     * Shows the ProgressBar, hides the Retry Button and invokes {@link #searchTracks()}
     * */
    @Override
    public void onClickRetry() {
        getView().showRetryProgressBar(true);
        getView().showRetryBtn(false);

        searchTracks();
    }

    /**
     * Invokes the search Api and updates the local database, invokes {@link #onSearchDone()} on
     * success and {@link #onSearchError(Throwable)} on error
     * @see TrackRepository#search(String, String, String)
     * */
    private void searchTracks() {
        RxUtil.dispose(mDisposable);

        mDisposable = mTrackRepository.search("star", "au", "movie")
                .map(this::updateDbTracks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> onSearchDone(), this::onSearchError);
    }

    /**
     * Updates the local database and hides the ProgressBar on SwipeRefreshLayout
     * @param searchResult The SearchResult which contains the {@link List<Track>} that would be
     *                     saved on the local database
     * @return The original SearchResult this method is created this way to use the method reference
     * instead of Lambda in invoking this method
     * @see #searchTracks()
     * */
    private SearchResult updateDbTracks(SearchResult searchResult) {
        mTrackRepository.updateDbTracks(searchResult.getResults());
        getView().setRefreshing(false);

        return searchResult;
    }

    /**
     * Invokes {@link #displayTrackGroups(List)} and hides No network connection message
     * */
    private void onSearchDone() {
        getView().showNoNetworkConnectionMessage(false);
        displayTrackGroups(mTrackRepository.getTrackGroups());
    }

    /**
     * Queries the local database to get a list of TrackGroups then invoke
     * {@link #onGetDbTrackGroupsDone(List)} if search failed because of a connection error,
     * otherwise hide ProgressBar on SwipeRefreshLayout and print the error's stack trace
     * @param throwable The search error
     * @see TrackRepository#getDbTrackGroups()
     * */
    private void onSearchError(Throwable throwable) {
        RxUtil.dispose(mDisposable);

        if (NetworkUtil.isConnectionError(throwable)) {
            mDisposable = mTrackRepository.getDbTrackGroups()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onGetDbTrackGroupsDone);
        } else {
            getView().setRefreshing(false);
            throwable.printStackTrace();
        }
    }

    /**
     * Displays the list of TrackGroups and shows the no network connection message
     * if size is more than 0, invoke {@link #showNoInternetMessage(boolean)} and hide the
     * no network connection message otherwise. Also hides the ProgressBar on SwipeRefreshLayout
     * @param trackGroups list of TrackGroups to be displayed
     * */
    private void onGetDbTrackGroupsDone(List<TrackGroup> trackGroups) {
        getView().setRefreshing(false);

        if (trackGroups.size() > 0) {
            getView().showNoNetworkConnectionMessage(true);
            displayTrackGroups(trackGroups);
        } else {
            getView().showNoInternetMessage(false);
            showNoInternetMessage(true);
        }
    }

    /**
     * Displays the list of TrackGroups, hides the retry ProgressBar and
     * invokes {@link #showNoInternetMessage(boolean)}
     * @param trackGroups list of TrackGroups to be displayed
     * */
    private void displayTrackGroups(List<TrackGroup> trackGroups) {
        showNoInternetMessage(false);
        getView().showRetryProgressBar(false);
        getView().displayTracksGroups(trackGroups);
    }

    /**
     * Sets the visibility of no internet message and show retry Button, enable or disable
     * SwipeRefreshLayout
     * @param show Boolean flag
     * */
    private void showNoInternetMessage(boolean show) {
        getView().showNoInternetMessage(show);
        getView().showRetryBtn(show);
        getView().enableSwipeRefresh(!show);
    }

    /**
     * Listens to the click event of the displayed tracks and opens the track details screen
     * */
    private void onTrackClicked() {
        mOnTrackClickedDisposable = getView().onTrackClicked()
                .subscribe(track -> getView().gotoTrackDetails(track));
    }

    /**
     * Disposes disposables
     * */
    @Override
    protected void onDestroy() {
        RxUtil.dispose(mDisposable);
        RxUtil.dispose(mOnTrackClickedDisposable);

        super.onDestroy();
    }
}
