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

public class TracksGroupAdapter extends RecyclerView.Adapter<TracksGroupViewHolder> {
    private final PublishSubject<Track> mSubject;

    private List<TrackGroup> mTrackGroups;

    @Inject
    TracksGroupAdapter() {
        mTrackGroups = new ArrayList<>();
        mSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public TracksGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tracks_group, parent, false);

        return new TracksGroupViewHolder(parent.getContext(), view);
    }

    @Override
    public int getItemCount() {
        return mTrackGroups.size();
    }

    public void setData(List<TrackGroup> trackGroups) {
        mTrackGroups = trackGroups;
    }

    PublishSubject<Track> onTrackClicked() {
        return mSubject;
    }

    @Override
    public void onBindViewHolder(@NonNull TracksGroupViewHolder holder, int position) {
        TrackGroup trackGroup = mTrackGroups.get(position);

        holder.setHeader(trackGroup.getHeader());
        holder.setTracks(trackGroup.getTracks());
        holder.setPublishSubject(mSubject);
    }
}
