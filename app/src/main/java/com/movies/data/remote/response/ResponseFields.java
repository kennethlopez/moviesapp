package com.movies.data.remote.response;

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
