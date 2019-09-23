package com.movies.ui.base;

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
