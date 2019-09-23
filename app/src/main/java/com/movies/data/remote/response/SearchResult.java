package com.movies.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult implements ResponseFields.Result{
    @SerializedName(RESULT_COUNT)
    @Expose
    private int resultCount;

    @SerializedName(RESULTS)
    @Expose
    private List<Track> results;

    public int getResultCount() {
        return resultCount;
    }

    public List<Track> getResults() {
        return results;
    }
}
