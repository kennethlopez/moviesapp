package com.movies.data.remote.response;

public interface ResponseFields {
    interface Result {
        String RESULT_COUNT = "resultCount";
        String RESULTS = "results";
    }

    interface Track {
        String KIND = "kind";
        String TRACK_ID = "trackId";
        String TRACK_NAME = "trackName";
        String PREVIEW_URL = "previewUrl";
        String ARTWORK_URL30 = "artworkUrl30";
        String ARTWORK_URL60 = "artworkUrl60";
        String ARTWORK_URL100 = "artworkUrl100";
        String TRACK_PRICE = "trackPrice";
        String COUNTRY = "country";
        String CURRENCY = "currency";
        String PRIMARY_GENRE_NAME = "primaryGenreName";
        String CONTENT_ADVISORY_RATING = "contentAdvisoryRating";
        String SHORT_DESCRIPTION = "shortDescription";
        String LONG_DESCRIPTION = "longDescription";
    }
}
