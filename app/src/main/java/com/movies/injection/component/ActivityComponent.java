package com.movies.injection.component;


import com.movies.injection.ActivityScope;
import com.movies.injection.module.ActivityModule;
import com.movies.ui.home.HomeActivity;
import com.movies.ui.home.TrackGroupViewHolder;
import com.movies.ui.track.details.TrackDetailsActivity;
import com.movies.ui.video.VideoPlayerActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent extends AppComponent {
    void inject(HomeActivity homeActivity);

    void inject(TrackGroupViewHolder trackGroupViewHolder);

    void inject(TrackDetailsActivity trackDetailsActivity);

    void inject(VideoPlayerActivity videoPlayerActivity);
}
