package com.smartsoft.ssutil.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class SingleItemCache<T> {

    private final String ONLY_KEY = "onlyKey";

    private final LoadingCache<String, T> cache;

    public SingleItemCache(final SingleItemLoader<T> singleItemLoader) {
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(4)
                .maximumSize(1)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, T>() {
                            @Override
                            public T load(final String onlyKey) throws Exception {
                                return singleItemLoader.load();
                            }
                        });

    }

    public T get() {
        try {
            return cache.get(ONLY_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface SingleItemLoader<T> {
        T load();
    }

}
