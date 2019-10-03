package com.movies.data.repository;

import java.util.List;

public abstract class BaseRepository<T> {
    abstract long insert(T item);

    abstract long[] insert(List<T> items);
}
