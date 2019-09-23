package com.movies.data.local.dao;

import com.movies.data.local.model.TrackModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface TrackDao extends BaseDao<TrackModel> {
    @Query("select * from tracks")
    Flowable<List<TrackModel>> getTracks();
}
