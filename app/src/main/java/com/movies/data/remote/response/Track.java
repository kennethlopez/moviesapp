package com.movies.data.remote.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.movies.data.local.model.TrackModel;

import java.util.ArrayList;
import java.util.List;

public class Track implements ResponseFields.Track {
    @SerializedName(KIND)
    @Expose
    private String kind;

    @SerializedName(TRACK_ID)
    @Expose
    private long trackId;

    @SerializedName(TRACK_NAME)
    @Expose
    private String trackName;

    @SerializedName(PREVIEW_URL)
    @Expose
    private String previewUrl;

    @SerializedName(ARTWORK_URL30)
    @Expose
    private String artworkUrl30;

    @SerializedName(ARTWORK_URL60)
    @Expose
    private String artworkUrl60;

    @SerializedName(ARTWORK_URL100)
    @Expose
    private String artworkUrl100;

    @SerializedName(TRACK_PRICE)
    @Expose
    private double trackPrice;

    @SerializedName(COUNTRY)
    @Expose
    private String country;

    @SerializedName(CURRENCY)
    @Expose
    private String currency;

    @SerializedName(PRIMARY_GENRE_NAME)
    @Expose
    private String primaryGenreName;

    @SerializedName(CONTENT_ADVISORY_RATING)
    @Expose
    private String contentAdvisoryRating;

    @SerializedName(SHORT_DESCRIPTION)
    @Expose
    private String shortDescription;

    @SerializedName(LONG_DESCRIPTION)
    @Expose
    private String longDescription;

    public String getKind() {
        return kind;
    }

    public long getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public TrackModel toTrackModel() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), TrackModel.class);
    }

    public static List<TrackModel> toTrackModels(List<Track> tracks) {
        if (tracks != null) {
            List<TrackModel> models = new ArrayList<>();

            for (Track track : tracks) {
                models.add(track.toTrackModel());
            }

            return models;
        }

        return null;
    }
}
