package com.movies.injection.component;


import com.movies.injection.ActivityScope;
import com.movies.injection.module.ActivityModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent extends AppComponent {
}
