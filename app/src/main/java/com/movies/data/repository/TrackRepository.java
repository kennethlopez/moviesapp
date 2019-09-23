package com.movies.data.repository;

import com.movies.data.remote.ApiService;
import com.movies.data.local.dao.TrackDao;
import com.movies.data.local.model.TrackModel;
import com.movies.data.remote.response.SearchResult;
import com.movies.data.remote.response.Track;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class TrackRepository implements BaseRepository<TrackModel> {
    private TrackDao mTrackDao;
    private ApiService mApiService;

    @Inject
    public TrackRepository(ApiService apiService, TrackDao trackDao) {
        mApiService = apiService;
        mTrackDao = trackDao;
    }

    @Override
    public long insert(TrackModel item) {
        return mTrackDao.insert(item);
    }

    @Override
    public long[] insert(List<TrackModel> items) {
        return mTrackDao.insert(items);
    }

    public Single<SearchResult> search(String term, String country, String media) {
        return mApiService.search(term, country, media);
    }

    public void saveTracks(List<Track> tracks) {
        insert(Track.toTrackModels(tracks));
    }
}