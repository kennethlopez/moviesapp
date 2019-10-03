package com.movies.ui.base;


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * {@link #attachView(BaseView)} and {@link #detachView()}. It also handles keeping a reference
 * to the view that can be accessed from the children classes by calling {@link #getView()}.
 */
public abstract class BasePresenter <T extends BaseView> implements Presenter<T> {
    private T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }

    protected final T getView() {
        return mView;
    }

    protected void onStart() {}

    protected void onResume() {}

    protected void onPause() {}

    protected void onStop() {}

    protected void onDestroy() {
        detachView();
    }
}
