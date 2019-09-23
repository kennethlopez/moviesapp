package com.movies.data.local;

import com.movies.data.local.dao.TrackDao;
import com.movies.data.local.model.TrackModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TrackModel.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "moviesapp-db";

    public abstract TrackDao trackDao();
}
