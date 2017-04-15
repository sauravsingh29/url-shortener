package com.ss.url.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Saurav on 14-04-2017.
 */
public abstract class AppCache<K> {

    protected Map<String, K> cache = new ConcurrentHashMap();

    /**
     * Method to check whether cache contains the key or not.
     *
     * @param key
     * @return boolean if found true else false.
     */
    public boolean contains(final String key) {
        return cache.containsKey(key);
    }

    /**
     * Method to find object from cache based on provided key.
     *
     * @param key
     * @return of type K object
     */
    public K getFromCache(final String key) {
        return cache.get(key);
    }

    /**
     * Add key and value to cache
     *
     * @param key type of String
     * @param val type of E
     */
    public void addToCache(final String key, final K val) {
        cache.put(key, val);

    }

}
