package com.movies.ui.home;

import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.SearchResult;
import com.movies.data.repository.TrackRepository;
import com.movies.injection.ConfigPersistent;
import com.movies.ui.base.BasePresenter;
import com.movies.util.NetworkUtil;
import com.movies.util.RxUtil;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private final TrackRepository mTrackRepository;

    private Disposable mDisposable;
    private Disposable mOnTrackClickedDisposable;

    @Inject
    HomePresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
    }

    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);

        getView().initViews();
        searchTracks();
        onTrackClicked();
    }

    @Override
    public void onRefresh() {
        getView().setRefreshing(true);
        searchTracks();
    }

    @Override
    public void onClickRetry() {
        getView().showRetryProgressBar(true);
        getView().showRetryBtn(false);

        searchTracks();
    }

    private void searchTracks() {
        RxUtil.dispose(mDisposable);

        mDisposable = mTrackRepository.search("star", "au", "movie")
                .map(this::updateDbTracks)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> onSearchDone(), this::onSearchError);
    }

    private SearchResult updateDbTracks(SearchResult searchResult) {
        mTrackRepository.updateDbTracks(searchResult.getResults());
        getView().setRefreshing(false);

        return searchResult;
    }

    private void onSearchDone() {
        getView().showNoNetworkConnectionMessage(false);
        displayTrackGroups(mTrackRepository.getTrackGroups());
    }

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

    private void displayTrackGroups(List<TrackGroup> trackGroups) {
        showNoInternetMessage(false);
        getView().showRetryProgressBar(false);
        getView().displayTracksGroups(trackGroups);
    }

    private void showNoInternetMessage(boolean show) {
        getView().showNoInternetMessage(show);
        getView().showRetryBtn(show);
        getView().enableSwipeRefresh(!show);
    }

    private void onTrackClicked() {
        mOnTrackClickedDisposable = getView().onTrackClicked()
                .subscribe(track -> getView().gotoTrackDetails(track));
    }

    @Override
    protected void onDestroy() {
        RxUtil.dispose(mDisposable);
        RxUtil.dispose(mOnTrackClickedDisposable);

        super.onDestroy();
    }
}
