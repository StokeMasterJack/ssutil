package com.smartsoft.ssutil;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import java.io.File;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ReloadingProperties {

    private final String ONLY_KEY = "onlyKey";

    private final LoadingCache<String, ImmutableMap<String, String>> cache;

    public ReloadingProperties(final File propertiesFile) {
        this(propertiesFile, 15);
    }

    public ReloadingProperties(final File propertiesFile, int expireAfter /* minutes */) {
        cache = CacheBuilder.newBuilder()
                .concurrencyLevel(4)
                .maximumSize(1)
                .expireAfterWrite(expireAfter, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, ImmutableMap<String, String>>() {
                            @Override
                            public ImmutableMap<String, String> load(final String onlyKey) throws Exception {
                                Reader r = Files.newReader(propertiesFile, Charset.defaultCharset());
                                Properties p = new Properties();
                                p.load(r);
                                return Maps.fromProperties(p);
                            }
                        });

    }

    public ImmutableMap<String, String> getProperties() {
        try {
            return cache.get(ONLY_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String propertyName) {
        return getProperties().get(propertyName);
    }

}
