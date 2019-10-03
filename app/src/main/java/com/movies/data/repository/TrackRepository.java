package com.movies.data.repository;

import com.movies.data.misc.TrackGroup;
import com.movies.data.remote.ApiService;
import com.movies.data.local.dao.TrackDao;
import com.movies.data.remote.response.SearchResult;
import com.movies.data.remote.response.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static com.movies.data.remote.response.ResponseFields.Track.PRIMARY_GENRE_NAME;

public class TrackRepository {
    private final TrackDao mTrackDao;
    private final ApiService mApiService;

    private SearchResult mSearchResult;
    private List<Track> mGroupedTracks;
    private List<TrackGroup> mTrackGroups = new ArrayList<>();

    @Inject
    TrackRepository(ApiService apiService, TrackDao trackDao) {
        mApiService = apiService;
        mTrackDao = trackDao;
    }

    /** Invokes {@link TrackDao#updateTracks(List)} which updates the local database
     * @param tracks list that would be used to update the local database
     */
    public void updateDbTracks(List<Track> tracks) {
        mTrackDao.updateTracks(Track.toTrackEntities(tracks));
    }

    public Flowable<List<TrackGroup>> getDbTrackGroups() {
        return mTrackDao.getTracks()
                .map(trackEntities -> groupTracks(Track.fromTrackEntities(trackEntities)));
    }

    /**
     * Invokes {@link ApiService#search(String, String, String)} which makes an http request.
     * Keeps a reference of the {@link SearchResult}.
     * @param term Value for request parameter "term"
     * @param country Value for request parameter "country"
     * @param media Value for request parameter "media"
     * @return the original {@link Single<SearchResult>} instance
     * */
    public Single<SearchResult> search(String term, String country, String media) {
        return mApiService.search(term, country, media)
                .map(searchResult -> mSearchResult = searchResult);
    }

    /**
     * This method will group the {@link Track} list on the {@link SearchResult}
     * @see #groupTracks(List)
     * @return A new or old {@link TrackGroup} list depending on whether the {@link SearchResult}
     * has been updated
     * */
    public List<TrackGroup> getTrackGroups() {
        if (mSearchResult != null) {
            List<Track> tracks = mSearchResult.getResults();

            if (!tracks.equals(mGroupedTracks)) {
                mTrackGroups = groupTracks(tracks);
            }
        }

        return mTrackGroups;
    }

    /**
     * This method groups a {@link Track} list with the same {@link PRIMARY_GENRE_NAME}
     * @param tracks Track list that will be grouped
     * @return A {@link TrackGroup} list which contains a list of the grouped tracks
     * */
    @SuppressWarnings("JavadocReference")
    private List<TrackGroup> groupTracks(List<Track> tracks) {
        List<TrackGroup> trackGroups = new ArrayList<>();

        for (Track track : tracks) {
            addToTrackGroups(trackGroups, track);
        }

        mGroupedTracks = tracks;
        return trackGroups;
    }

    /**
     * This method adds a {@link Track} to the same {@link TrackGroup} on the list or
     * @param trackGroups List of TrackGroup where the Track will be added
     * @param track Track that will be added to the list of TrackGroup
     * */
    private void addToTrackGroups(List<TrackGroup> trackGroups, Track track) {
        for (TrackGroup trackGroup : trackGroups) {
            if (trackGroup.isSameGroup(track)) {
                trackGroup.add(track);
                return; // no need to continue since track is already added on the list
            }
        }

        // create a new TrackGroup then add it to the list
        TrackGroup trackGroup = new TrackGroup();
        trackGroup.add(track);

        trackGroups.add(trackGroup);
    }

}