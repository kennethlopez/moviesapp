package com.movies.injection.component;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.movies.data.remote.ApiService;
import com.movies.data.local.AppDatabase;
import com.movies.data.local.dao.TrackDao;
import com.movies.injection.ApplicationContext;
import com.movies.injection.module.AppModule;
import com.movies.injection.module.RoomModule;
import com.movies.util.RxBus;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {
    Application application();

    @ApplicationContext Context context();

    Gson gson();

    OkHttpClient.Builder okHttpClientBuilder();

    ApiService apiService();

    RxBus rxBus();

    AppDatabase appDatabase();

    TrackDao trackDao();
}
