package com.movies.data.local.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.movies.data.remote.response.ResponseFields;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tracks",
        indices = @Index(value = {TrackEntity.TRACK_ID}, unique = true))
public class TrackEntity implements ResponseFields.Track {
    static final String TRACK_ID = "track_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    private int id;

    @SerializedName(ResponseFields.Track.TRACK_ID)
    @ColumnInfo(name = TrackEntity.TRACK_ID)
    @Expose
    private long trackId;

    @SerializedName(TRACK_NAME)
    @ColumnInfo(name = "track_name")
    @Expose
    private String trackName;

    @SerializedName(PREVIEW_URL)
    @ColumnInfo(name = "preview_url")
    @Expose
    private String previewUrl;

    @SerializedName(ARTWORK_URL100)
    @ColumnInfo(name = "artwork_url_100")
    @Expose
    private String artworkUrl100;

    @SerializedName(TRACK_PRICE)
    @ColumnInfo(name = "track_price")
    @Expose
    private double trackPrice;

    @SerializedName(RELEASE_DATE)
    @ColumnInfo(name = "release_date")
    @Expose
    private String releaseDate;

    @SerializedName(COUNTRY)
    @ColumnInfo(name = "country")
    @Expose
    private String country;

    @SerializedName(CURRENCY)
    @ColumnInfo(name = "currency")
    @Expose
    private String currency;

    @SerializedName(PRIMARY_GENRE_NAME)
    @ColumnInfo(name = "primary_genre_name")
    @Expose
    private String primaryGenreName;

    @SerializedName(LONG_DESCRIPTION)
    @ColumnInfo(name = "long_description")
    @Expose
    private String longDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
