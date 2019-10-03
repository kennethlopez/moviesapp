package com.movies.data.local;

import com.movies.data.local.dao.TrackDao;
import com.movies.data.local.entity.TrackEntity;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Database class. This is where the entities are included to the database and the {@link Dao} for
 * those entities
 * */
@Database(entities = {TrackEntity.class},
        version = 3,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "moviesapp-db";

    public abstract TrackDao trackDao();
}
