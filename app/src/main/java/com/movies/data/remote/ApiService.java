package com.movies.data.remote;

import com.movies.data.remote.response.SearchResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search")
    Single<SearchResult> search(
            @Query("term") String terms,
            @Query("country") String country,
            @Query("media") String media
    );
}
