package com.movies.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.R;
import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.response.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.subjects.PublishSubject;

/**
 * RecyclerView adapter for displaying a {@link TrackGroup} list
 * */
public class TracksGroupAdapter extends RecyclerView.Adapter<TrackGroupViewHolder> {
    private final PublishSubject<Track> mSubject;

    private List<TrackGroup> mTrackGroups;

    @Inject
    TracksGroupAdapter() {
        mTrackGroups = new ArrayList<>();
        mSubject = PublishSubject.create();
    }

    /**
     * Inflates the layout and creates the ViewHolder to represent the items
     * */
    @NonNull
    @Override
    public TrackGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tracks_group, parent, false);

        return new TrackGroupViewHolder(parent.getContext(), view);
    }

    /**
     * @return The size of the items
     * */
    @Override
    public int getItemCount() {
        return mTrackGroups.size();
    }

    /**
     * Sets the list of TrackGroups which will be out on the RecyclerView
     * @param trackGroups the list of TrackGroups that will be displayed
     * */
    public void setData(List<TrackGroup> trackGroups) {
        mTrackGroups = trackGroups;
    }

    /**
     * This is used to access the {@link Track} of the clicked item displayed on the RecyclerView,
     * We do it this way because we can't access the data using only the position of the clicked item
     * because we still wouldn't know which group that item belonged to
     * */
    PublishSubject<Track> onTrackClicked() {
        return mSubject;
    }

    /**
     * Sets the {@link TrackGroupViewHolder} data
     * @param holder The ViewHolder for the items
     * @param position The item position
     * */
    @Override
    public void onBindViewHolder(@NonNull TrackGroupViewHolder holder, int position) {
        TrackGroup trackGroup = mTrackGroups.get(position);

        holder.setHeader(trackGroup.getHeader());
        holder.setTracks(trackGroup.getTracks());
        holder.setPublishSubject(mSubject);
    }
}
