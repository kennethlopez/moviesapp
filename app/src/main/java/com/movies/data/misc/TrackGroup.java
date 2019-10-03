package com.movies.data.misc;

import com.movies.data.remote.response.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent {@link Track}s in the same group
 *
 * @see #isSameGroup(Track) for the grouping conditions
 * */
public class TrackGroup {
    private String mHeader;
    private List<Track> mTracks;

    /**
     * @return Returns the group header
     * */
    public String getHeader() {
        return mHeader;
    }

    /**
     * @return Returns the {@link Track}s in this group
     * */
    public List<Track> getTracks() {
        return mTracks;
    }

    /**
     * Indicates whether the track belongs to this group
     * @param track The {@link Track} to be checked
     * @return {@code true} if track belongs to this group, {@code false} otherwise
     * */
    public boolean isSameGroup(Track track) {
        return mHeader.contentEquals(track.getPrimaryGenreName());
    }

    /**
     * Adds a {@link Track} to this TrackGroup
     * @param track The Track that would be added to this group
     * */
    public void add(Track track) {
        if (mTracks == null) {
            mTracks = new ArrayList<>();

            // assign value to mHeader only once
            if (mHeader == null) {
                mHeader = track.getPrimaryGenreName();
            }
        }

        mTracks.add(track);
    }
}
