package task.LRU;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import task.model.StringObject;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class LRUCache {
    private static final Logger log = Logger.getLogger(LRUCache.class.getName());
    private final AtomicInteger counterEvictions = new AtomicInteger(0);
    private static final int DEFAULT_MAX_SIZE = 100000;
    private final Cache<Integer, StringObject> cache;

    public LRUCache(int capacity) {
        cache = CacheBuilder.newBuilder()
                .maximumSize(capacity)
                .removalListener(
                        (RemovalListener<Integer, StringObject>) removal -> {
                            log.info("Removal: " + removal.getKey() + "/" + removal.getValue());
                            counterEvictions.getAndIncrement();
                        })
                .build();
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
        log.info(cache.asMap().toString());
    }

    public void printStatistics() {
        log.info("Count of evictions: " + counterEvictions.get());
    }

}
