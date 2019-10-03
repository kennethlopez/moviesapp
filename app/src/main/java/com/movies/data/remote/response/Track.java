package com.movies.data.remote.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.movies.data.local.entity.TrackEntity;
import com.movies.util.AppUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A POJO class to represent Track from the remote Api
 * */
@SuppressWarnings("unused")
public class Track implements ResponseFields.Track {
    @SerializedName(TRACK_NAME)
    @Expose
    private String trackName;

    @SerializedName(TRACK_ID)
    @Expose
    private long trackId;

    @SerializedName(PREVIEW_URL)
    @Expose
    private String previewUrl;

    @SerializedName(ARTWORK_URL100)
    @Expose
    private String artworkUrl100;

    @SerializedName(TRACK_PRICE)
    @Expose
    private double trackPrice;

    @SerializedName(RELEASE_DATE)
    @Expose
    private String releaseDate;

    @SerializedName(COUNTRY)
    @Expose
    private String country;

    @SerializedName(CURRENCY)
    @Expose
    private String currency;

    @SerializedName(PRIMARY_GENRE_NAME)
    @Expose
    private String primaryGenreName;

    @SerializedName(LONG_DESCRIPTION)
    @Expose
    private String longDescription;

    /**
     * This method converts a {@link Track} list into a {@link TrackEntity} list by serializing
     * the {@link Track} list into JSON and deserialize it into a {@link TrackEntity} list
     * @param tracks Track list that will be converted
     * @return A {@link TrackEntity} list
     * */
    public static List<TrackEntity> toTrackEntities(List<Track> tracks) {
        Gson gson = AppUtil.getGson();
        Type type = new TypeToken<List<TrackEntity>>() {}.getType();

        return gson.fromJson(gson.toJson(tracks, type), type);
    }

    public static List<Track> fromTrackEntities(List<TrackEntity> trackEntities) {
        Gson gson = AppUtil.getGson();
        Type type = new TypeToken<List<Track>>() {}.getType();

        return gson.fromJson(gson.toJson(trackEntities, type), type);
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

    public String getLongDescription() {
        return longDescription;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
