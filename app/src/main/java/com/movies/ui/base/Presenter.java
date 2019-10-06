package com.movies.ui.base;


/**
 * Base class that implements the {@link BasePresenter} interface and provides lifecycle methods.
 * It also keeps a reference to the view that can be accessed from the children classes
 * by calling {@link #getView()}.
 */
public abstract class Presenter<V extends BaseView> implements BasePresenter<V> {
    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    protected boolean isViewAttached() {
        return mView != null;
    }

    protected final V getView() {
        return mView;
    }

    // methods for lifecycle awareness

    protected void onStart() {}

    protected void onResume() {}

    protected void onPause() {}

    protected void onStop() {}

    protected void onDestroy() {
        detachView();
    }
}
