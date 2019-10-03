package com.movies.ui.track.details;

import com.movies.data.remote.response.Track;
import com.movies.injection.ConfigPersistent;
import com.movies.ui.base.BasePresenter;
import com.movies.util.AppUtil;
import com.movies.util.MediaPlayerUtil;
import com.movies.util.RxUtil;

import java.text.ParseException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.movies.util.Constants.AppConstants.DISPLAY_DATE_PATTERN;
import static com.movies.util.Constants.AppConstants.RESPONSE_DATE_PATTERN;

@ConfigPersistent
public class TrackDetailsPresenter extends BasePresenter<TrackDetailsContract.View> implements
        TrackDetailsContract.Presenter {
    private final MediaPlayerUtil mMediaPlayerUtil;

    private Disposable mDisposable;

    @Inject
    TrackDetailsPresenter(MediaPlayerUtil mediaPlayerUtil)  {
        mMediaPlayerUtil = mediaPlayerUtil;
    }

    @Override
    public void attachView(TrackDetailsContract.View view) {
        super.attachView(view);

        getView().initViews();
    }

    @Override
    public void setTrack(Track track) {
        setPreviewImage(track);
        getView().setTrackImage(track.getArtworkUrl100());
        getView().setTitle(track.getTrackName());
        getView().setDescription(track.getLongDescription());
        displayDate(track);
    }

    @Override
    public void onClickPlayFab() {
        System.out.println("Play video");
    }

    private void setPreviewImage(Track track) {
        mDisposable = mMediaPlayerUtil.getPreviewImage(track.getPreviewUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    if (isViewAttached()) {
                        getView().setPreviewImage(bitmap);
                    }
                }, Throwable::printStackTrace);
    }

    private void displayDate(Track track) {
        try {
            String date = AppUtil.formatDate(RESPONSE_DATE_PATTERN,
                    DISPLAY_DATE_PATTERN,
                    track.getReleaseDate());

            getView().setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        RxUtil.dispose(mDisposable);

        super.onDestroy();
    }
}
