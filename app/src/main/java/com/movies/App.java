package com.movies;

import android.app.Application;
import android.content.Context;

import com.movies.injection.component.AppComponent;
import com.movies.injection.component.DaggerAppComponent;
import com.movies.injection.module.AppModule;

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
