package com.movies.data.repository;

import java.util.List;

public interface BaseRepository<T> {
    long insert(T item);

    long[] insert(List<T> items);
}
