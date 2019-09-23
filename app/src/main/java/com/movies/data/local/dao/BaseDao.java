package com.movies.data.local.dao;

import java.util.List;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<T> items);

    @Update
    void update(T item);

    @Update
    void update(List<T> items);
}
