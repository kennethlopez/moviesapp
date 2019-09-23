package com.movies.injection.module;

import com.movies.App;
import com.movies.data.local.AppDatabase;
import com.movies.data.local.dao.TrackDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private AppDatabase mAppDatabase;

    public RoomModule(App app) {
        mAppDatabase = Room.databaseBuilder(app, AppDatabase.class, AppDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase() {
        return mAppDatabase;
    }

    @Provides
    @Singleton
    TrackDao providesTrackDao() {
        return mAppDatabase.trackDao();
    }
}
