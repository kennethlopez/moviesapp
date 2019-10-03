package com.movies.data.misc;

import com.movies.data.remote.response.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackGroup {
    private String mHeader;
    private List<Track> mTracks;

    public String getHeader() {
        return mHeader;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public boolean isSameGroup(Track track) {
        return mHeader.contentEquals(track.getPrimaryGenreName());
    }

    public void add(Track track) {
        if (mTracks == null || mTracks.size() == 0) {
            mTracks = new ArrayList<>();
            mHeader = track.getPrimaryGenreName();
        }

        mTracks.add(track);
    }
}
