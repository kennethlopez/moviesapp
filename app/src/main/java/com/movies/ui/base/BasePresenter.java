package com.movies.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend {@link Presenter}
 * indicating the {@link BaseView} type that wants to be attached with.
 */
public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
