package com.movies;

import android.app.Application;
import android.content.Context;

import com.movies.injection.component.AppComponent;
import com.movies.injection.component.DaggerAppComponent;
import com.movies.injection.module.AppModule;
import com.movies.injection.module.RoomModule;

public class App extends Application {
    private AppComponent mAppComponent;

    /**
     * @param context Context
     * @return The App instance
     * */
    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    /**
     * Builds the {@link AppComponent} using Dagger
     * @return The built AppComponent
     * */
    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .roomModule(new RoomModule(this))
                    .build();
        }

        return mAppComponent;
    }
}
