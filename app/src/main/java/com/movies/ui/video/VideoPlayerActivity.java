package com.movies.ui.video;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.movies.R;
import com.movies.injection.component.ActivityComponent;
import com.movies.ui.base.BaseActivity;
import com.movies.util.MediaPlayerUtil;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.movies.util.Constants.AppConstants.KEY_VIDEO_URL;

/**
 * {@link VideoPlayerPresenter}'s View
 * */
public class VideoPlayerActivity extends BaseActivity implements VideoPlayerContact.View,
        PlaybackPreparer {
    @Inject VideoPlayerPresenter mPresenter;
    @Inject MediaPlayerUtil mMediaPlayerUtil;

    private SimpleExoPlayer mPlayer;
    private DefaultTrackSelector mTrackSelector;
    private DefaultTrackSelector.Parameters mTrackSelectorParameters;

    /**
     * Creates a {@link Bundle} with extras
     * @param videoUrl A string to be put on as a Bundle extra
     * @return A new Bundle
     * */
    public static Bundle createExtras(String videoUrl) {
        Bundle args = new Bundle();
        args.putString(KEY_VIDEO_URL, videoUrl);

        return args;
    }

    /**
     * Injects the class to the {@link ActivityComponent}.
     * Binds the class to the ButterKnife.
     * Attaches the class to the Presenter.
     * Attaches the Presenter to the BaseActivity
     * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        getComponent().inject(this);
        ButterKnife.bind(this);

        mPresenter.attachView(this);

        super.attachPresenter(mPresenter);
    }

    /**
     * Initializes the Views
     * */
    @Override
    public void initViews() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            String videoUrl = args.getString(KEY_VIDEO_URL);

            mPresenter.setVideoUrl(videoUrl);

            mTrackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            mPlayerView.setErrorMessageProvider(new PlayerErrorMessageProvider(this));
            mPlayerView.requestFocus();
        }
    }

    /**
     * Initializes the media player
     * @param videoUrl Link to the media file to be played
     * */
    @Override
    public void initializePlayer(String videoUrl) {
        TrackSelection.Factory trackSelectionFactory;
        trackSelectionFactory = new AdaptiveTrackSelection.Factory();

        RenderersFactory renderersFactory = mMediaPlayerUtil.buildRenderersFactory(false);
        mTrackSelector = new DefaultTrackSelector(trackSelectionFactory);
        mTrackSelector.setParameters(mTrackSelectorParameters);

        mPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, mTrackSelector);
        mPlayer.setPlayWhenReady(true);
        mPlayerView.setPlayer(mPlayer);
        mPlayerView.setPlaybackPreparer(this);
        mPlayer.prepare(mMediaPlayerUtil.createMediaSource(Uri.parse(videoUrl)), false, false);
    }

    /**
     * Pauses the media player
     * */
    @Override
    public void pausePlayerView() {
        mPlayerView.onPause();
    }

    /**
     * Resumes the media player
     * */
    @Override
    public void resumePlayerView() {
        mPlayerView.onResume();
    }

    /**
     * Indicates whether {@link #mPlayer} is initialized
     * @return {@code true} if {@link #mPlayer} is not null, {@code false} otherwise
     * */
    @Override
    public boolean playerInitialized() {
        return mPlayer != null;
    }

    /**
     * Releases the media player
     * */
    @Override
    public void releasePlayer() {
        mPlayer.release();
        mPlayer = null;
        mTrackSelector = null;
    }

    /**
     * Retry the media player
     * */
    @Override
    public void preparePlayback() {
        mPlayer.retry();
    }

    /**
     * See whether the player view wants to handle media or DPAD keys events.
     * @param event The KeyEvent
     * @return A boolean
     * */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mPlayerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }

    @BindView(R.id.activity_video_player_view) PlayerView mPlayerView;
}
