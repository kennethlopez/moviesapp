package com.movies.data.local.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.movies.data.remote.response.ResponseFields;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tracks",
        indices = @Index(value = {TrackModel.TRACK_ID}, unique = true))
public class TrackModel implements ResponseFields.Track {
    static final String TRACK_ID = "track_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;

    @SerializedName(KIND)
    @ColumnInfo(name = "kind")
    @Expose
    private String kind;

    @SerializedName(ResponseFields.Track.TRACK_ID)
    @ColumnInfo(name = TrackModel.TRACK_ID)
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

    @SerializedName(ARTWORK_URL30)
    @ColumnInfo(name = "artwork_url_30")
    @Expose
    private String artworkUrl30;

    @SerializedName(ARTWORK_URL60)
    @ColumnInfo(name = "artwork_url_60")
    @Expose
    private String artworkUrl60;

    @SerializedName(ARTWORK_URL100)
    @ColumnInfo(name = "artwork_url_100")
    @Expose
    private String artworkUrl100;

    @SerializedName(TRACK_PRICE)
    @ColumnInfo(name = "track_price")
    @Expose
    private double trackPrice;

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

    @SerializedName(CONTENT_ADVISORY_RATING)
    @ColumnInfo(name = "content_advisory_rating")
    @Expose
    private String contentAdvisoryRating;

    @SerializedName(SHORT_DESCRIPTION)
    @ColumnInfo(name = "short_description")
    @Expose
    private String shortDescription;

    @SerializedName(LONG_DESCRIPTION)
    @ColumnInfo(name = "long_description")
    @Expose
    private String longDescription;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
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

    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }

    public void setContentAdvisoryRating(String contentAdvisoryRating) {
        this.contentAdvisoryRating = contentAdvisoryRating;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
