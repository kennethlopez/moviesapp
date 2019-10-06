package com.movies.ui.track.details;

import com.movies.data.remote.response.Track;
import com.movies.injection.ConfigPersistent;
import com.movies.ui.base.Presenter;
import com.movies.ui.video.VideoPlayerActivity;
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

/**
 * {@link TrackDetailsActivity}'s Presenter
 * */
@ConfigPersistent
public class TrackDetailsPresenter extends Presenter<TrackDetailsContract.View> implements
        TrackDetailsContract.Presenter {
    private final MediaPlayerUtil mMediaPlayerUtil;

    private Track mTrack;
    private Disposable mDisposable;

    @Inject
    TrackDetailsPresenter(MediaPlayerUtil mediaPlayerUtil)  {
        mMediaPlayerUtil = mediaPlayerUtil;
    }

    /**
     * Attaches the {@link TrackDetailsContract.View} to the {@link Presenter}
     * */
    @Override
    public void attachView(TrackDetailsContract.View view) {
        super.attachView(view);

        getView().initViews();
    }

    /**
     * Display Track data on their corresponding View's
     * @param track The Track that was selected
     * */
    @Override
    public void setTrack(Track track) {
        mTrack = track;

        getView().setTrackImage(track.getArtworkUrl100());
        getView().setTitle(track.getTrackName());
        getView().setDescription(track.getLongDescription());
        displayDate();
        setPreviewImage();
    }

    /**
     * Opens the {@link VideoPlayerActivity} which will play the Track's preview video
     * */
    @Override
    public void onClickPlayFab() {
        getView().gotoVideoPlayer(mTrack.getPreviewUrl());
    }

    /**
     * Asynchronously gets a Bitmap image of the Track's preview video and puts it into the corresponding View
     * @see MediaPlayerUtil#getPreviewImage(String)
     * */
    private void setPreviewImage() {
        mDisposable = mMediaPlayerUtil.getPreviewImage(mTrack.getPreviewUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    if (isViewAttached()) {
                        getView().setPreviewImage(bitmap);
                    }
                }, Throwable::printStackTrace);
    }

    /**
     * Format and display Track's release date
     * */
    private void displayDate() {
        try {
            String date = AppUtil.formatDate(RESPONSE_DATE_PATTERN,
                    DISPLAY_DATE_PATTERN,
                    mTrack.getReleaseDate());

            getView().setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disposes the disposable
     * */
    @Override
    protected void onDestroy() {
        RxUtil.dispose(mDisposable);

        super.onDestroy();
    }
}
