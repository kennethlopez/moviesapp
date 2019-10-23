package com.movies.ui.base;

import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;

import com.movies.App;
import com.movies.injection.component.ActivityComponent;
import com.movies.injection.component.ConfigPersistentComponent;
import com.movies.injection.component.DaggerConfigPersistentComponent;
import com.movies.injection.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public abstract class BaseActivity<V extends BaseView> extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong sNextId = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent>
            sComponentsMap = new LongSparseArray<>();

    private ActivityComponent mComponent;
    private Presenter mPresenter;
    private long mActivityId;

    /**
     * @return The ActivityComponent
     * */
    public ActivityComponent getComponent() {
        return mComponent;
    }

    /**
     * @return The App instance
     * */
    protected App getApp() {
        return (App) getApplicationContext();
    }

    /**
     * Sets the Presenter and attaches a view into it
     * @param presenter The presenter
     * @param view The View that will be attached to the presenter
     * */
    protected void attachPresenter(BasePresenter<V> presenter, V view) {
        presenter.attachView(view);
        mPresenter = (Presenter) presenter;
        view.initViews();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : sNextId.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId, null);

        if (configPersistentComponent == null) {
            Log.d(TAG, String.format("Creating new ConfigPersistentComponent id=%d", mActivityId));
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(App.get(this).getAppComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        }
        mComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    // for presenter to have lifecycle awareness

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        if (mPresenter != null) mPresenter.onPause();

        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) mPresenter.onStop();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();

        if (!isChangingConfigurations()) {
            Log.d(TAG, String.format("Clearing ConfigPersistentComponent id=%d", mActivityId));
            sComponentsMap.remove(mActivityId);
        }

        super.onDestroy();
    }
}
