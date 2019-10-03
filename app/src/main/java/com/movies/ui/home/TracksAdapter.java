package com.movies.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.R;
import com.movies.data.remote.response.Track;
import com.movies.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TracksAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private List<Track> mTracks;

    @Inject
    TracksAdapter() {
        mTracks = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_track, parent, false);

        return new TrackViewHolder(parent.getContext(), view);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public void setData(List<Track> tracks) {
        mTracks = tracks;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = mTracks.get(position);

        String currencySymbol = AppUtil.getCurrencySymbol(track.getCountry(), track.getCurrency());
        String price = currencySymbol.concat(String.valueOf(track.getTrackPrice()));

        holder.setImage(track.getArtworkUrl100());
        holder.setTitle(track.getTrackName());
        holder.setPrice(price);
    }
}
