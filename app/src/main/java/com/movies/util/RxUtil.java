package com.movies.util;

import io.reactivex.disposables.Disposable;

/**
 * Utilities for RxJava
 * */
public class RxUtil {

    /**
     * Disposes the resource
     * @param disposable resource to be disposed
     * */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }
}
