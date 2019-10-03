package com.movies.data.remote.response;

import com.movies.data.local.entity.TrackEntity;

import java.util.List;

/**
 * Fields that corresponds to Api results, can also be used on Room database entities. See bellow
 * for an example use of these fields
 * @see TrackEntity
 * @see Track
 * @see com.movies.data.remote.response.Track#fromTrackEntities(List)
 * */
public interface ResponseFields {
    interface Result {
        String RESULT_COUNT = "resultCount";
        String RESULTS = "results";
    }

    interface Track {
        String TRACK_ID = "trackId";
        String TRACK_NAME = "trackName";
        String PREVIEW_URL = "previewUrl";
        String ARTWORK_URL100 = "artworkUrl100";
        String TRACK_PRICE = "trackPrice";
        String RELEASE_DATE = "releaseDate";
        String COUNTRY = "country";
        String CURRENCY = "currency";
        String PRIMARY_GENRE_NAME = "primaryGenreName";
        String LONG_DESCRIPTION = "longDescription";
    }
}
