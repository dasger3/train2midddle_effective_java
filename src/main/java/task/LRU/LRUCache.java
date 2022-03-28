package task.LRU;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import task.model.StringObject;

public class LRUCache {
    private static final int DEFAULT_MAX_SIZE = 100000;
    private final Cache<Integer, StringObject> cache;

    public LRUCache(int capacity) {
        cache = CacheBuilder.newBuilder().maximumSize(capacity).build();
    }

    public LRUCache() {
        cache = CacheBuilder.newBuilder().maximumSize(DEFAULT_MAX_SIZE).build();
    }

    public void set(int key, String value) {
        cache.put(key, new StringObject(value));
    }

    public StringObject get(int key) {
        return cache.getIfPresent(key);
    }

    public void printCache() {
        System.out.println(cache.asMap());
    }
}
