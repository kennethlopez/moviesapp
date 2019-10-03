package com.movies.ui.home;

import android.content.Context;
import android.view.View;

import com.movies.R;
import com.movies.data.remote.response.Track;
import com.movies.ui.base.BaseActivity;
import com.movies.util.AppUtil;
import com.movies.util.HorizontalMarginItemDecoration;
import com.movies.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * ViewHolder for {@link TracksGroupAdapter}
 * */
public class TrackGroupViewHolder extends RecyclerView.ViewHolder {
    @Inject TracksAdapter mTracksAdapter;

    private Context mContext;
    private PublishSubject<Track> mSubject;
    private List<Track> mTracks = new ArrayList<>();

    TrackGroupViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        mContext = context;
        mSubject = PublishSubject.create();

        ((BaseActivity) mContext).getComponent().inject(this);
        ButterKnife.bind(this, itemView);

        initTracksContainer();
    }

    /**
     * Initializes the RecyclerView which will hold the list of tracks
     * */
    private void initTracksContainer() {
        int horizontalMargin = AppUtil.dpToPx(8);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);

        mTracksContainer.setLayoutManager(layoutManager);
        mTracksContainer.setItemAnimator(new DefaultItemAnimator());
        mTracksContainer.addItemDecoration(new HorizontalMarginItemDecoration(horizontalMargin));
        mTracksContainer.setAdapter(mTracksAdapter);
        mTracksContainer.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                mTracksContainer,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        mSubject.onNext(mTracks.get(position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    /**
     * Sets the header
     * @param header The header
     * */
    void setHeader(String header) {
        mHeader.setText(header);
    }

    /**
     * Sets the list of Tracks which will be put on the RecyclerView
     * @param tracks The list of Tracks that will be displayed
     * */
    void setTracks(List<Track> tracks) {
        mTracks = tracks;
        mTracksAdapter.setData(tracks);
        mTracksAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the {@link PublishSubject<Track>}
     * @param subject The PublishSubject where the clicked Track will be emitted
     * @see #initTracksContainer()
     * */
    void setPublishSubject(PublishSubject<Track> subject) {
        mSubject = subject;
    }

    @BindView(R.id.item_tracks_group_header) AppCompatTextView mHeader;
    @BindView(R.id.item_tracks_group_recycler_view) RecyclerView mTracksContainer;
}
