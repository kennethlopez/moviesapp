package com.movies.data.local.dao;

import com.movies.data.local.entity.TrackEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Flowable;

@Dao
public abstract class TrackDao implements BaseDao<TrackEntity> {
    @Query("select * from tracks")
    public abstract Flowable<List<TrackEntity>> getTracks();

    @Query("delete from tracks")
    public abstract void deleteAllTracks();

    @Transaction
    public long[] updateTracks(List<TrackEntity> tracks) {
        deleteAllTracks();
        return insert(tracks);
    }
}
