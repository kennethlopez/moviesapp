package com.movies.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.movies.R;
import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.Track;
import com.movies.injection.component.ActivityComponent;
import com.movies.ui.base.BaseActivity;
import com.movies.ui.track.details.TrackDetailsActivity;
import com.movies.util.AppUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * {@link HomePresenter}'s View
 * */
public class HomeActivity extends BaseActivity implements HomeContract.View {
    @Inject HomePresenter mPresenter;
    @Inject TracksGroupAdapter mTracksGroupAdapter;

    /**
     * Injects the class to the {@link ActivityComponent}.
     * Binds the class to the ButterKnife.
     * Attaches the class to the Presenter.
     * Attaches the Presenter to the BaseActivity
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTracksGroupContainer.setLayoutManager(layoutManager);
        mTracksGroupContainer.setItemAnimator(new DefaultItemAnimator());
        mTracksGroupContainer.setAdapter(mTracksGroupAdapter);

        enableSwipeRefresh(false);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorAccent,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark);

        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onRefresh());
        mRetryBtn.setOnClickListener(v -> mPresenter.onClickRetry());
    }

    /**
     * Sets SwipeRefreshLayout refreshing
     * @param refreshing The refreshing flag
     * */
    @Override
    public void setRefreshing(boolean refreshing) {
        mSwipeRefresh.setRefreshing(refreshing);
    }

    /**
     * Sets the {@link TracksGroupAdapter}'s data
     * @param trackGroups The TrackGroup list
     * */
    @Override
    public void displayTracksGroups(List<TrackGroup> trackGroups) {
        mTracksGroupAdapter.setData(trackGroups);
        mTracksGroupAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the visibility of {@link #mNoInternetMessage}
     * @param show Flag used to set the Views visibility
     * */
    @Override
    public void showNoInternetMessage(boolean show) {
        AppUtil.showView(mNoInternetMessage, show);
    }

    /**
     * Sets the visibility of {@link #mRetryBtn}
     * @param show Flag used to set the Views visibility
     * */
    @Override
    public void showRetryBtn(boolean show) {
        AppUtil.showView(mRetryBtn, show);
    }

    /**
     * Sets the visibility of {@link #mProgressBar}
     * @param show Flag used to set the Views visibility
     * */
    @Override
    public void showRetryProgressBar(boolean show) {
        AppUtil.showView(mProgressBar, show);
    }

    /**
     * Enable or disable {@link #mSwipeRefresh}
     * @param enable Flag used to enable or disable the View
     * */
    @Override
    public void enableSwipeRefresh(boolean enable) {
        mSwipeRefresh.setEnabled(enable);
    }

    /**
     * Sets the visibility of {@link #mNoNetworkConnectionMessage}
     * @param show Flag used to set the Views visibility
     * */
    @Override
    public void showNoNetworkConnectionMessage(boolean show) {
        AppUtil.showView(mNoNetworkConnectionMessage, show);
    }

    /**
     * @return The {@link PublishSubject<Track>} of the {@link TracksGroupAdapter} which acts
     * like an item click listener. This will emit {@link Track} which was clicked
     * */
    @Override
    public PublishSubject<Track> onTrackClicked() {
        return mTracksGroupAdapter.onTrackClicked();
    }

    /**
     * Opens the {@link TrackDetailsActivity}
     * @param track Track that would be displayed on the Activity
     * */
    @Override
    public void gotoTrackDetails(Track track) {
        Bundle args = TrackDetailsActivity.createExtras(track);
        Intent intent = new Intent(this, TrackDetailsActivity.class);
        intent.putExtras(args);
        startActivity(intent);
    }

    @BindView(R.id.activity_home_tracks_group_recycler_view) RecyclerView mTracksGroupContainer;
    @BindView(R.id.activity_home_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.activity_home_no_internet_message) AppCompatTextView mNoInternetMessage;
    @BindView(R.id.activity_home_retry_button) AppCompatButton mRetryBtn;
    @BindView(R.id.activity_home_retry_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.activity_home_no_connection_layout) LinearLayout mNoNetworkConnectionMessage;
}