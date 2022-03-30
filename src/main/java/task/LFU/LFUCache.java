package task.LFU;

import lombok.NoArgsConstructor;
import task.model.StringObject;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class LFUCache {

    private static final int DEFAULT_MAX_SIZE = 100000;
    private int capacity = DEFAULT_MAX_SIZE;
    private final Map<Integer, HitRate> serviceCache = new ConcurrentHashMap<>();
    private final Map<Integer, StringObject> cache = new ConcurrentHashMap<>();


    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public Optional<StringObject> set(int key, String value) {
        StringObject deletedItem = null;
        StringObject v = cache.get(key);
        if (v == null) {
            if (serviceCache.size() == capacity) {
                Integer k = getKickedKey();
                deletedItem = cache.remove(k);
                serviceCache.remove(k);
            }
            serviceCache.put(key, new HitRate(key, new AtomicInteger(1), System.nanoTime()));
        } else { // Если ключ тот же, только увеличиваем частоту, время обновления и не заменяем
            HitRate hitRate = serviceCache.get(key);
            hitRate.hitCount.getAndAdd(1);
            hitRate.lastTime = System.nanoTime();
        }
        cache.put(key, new StringObject(value));
        return (deletedItem == null) ? Optional.empty() : Optional.of(deletedItem);
    }

    public StringObject get(int key) {
        StringObject v = cache.get(key);
        if (v != null) {
            HitRate hitRate = serviceCache.get(key);
            hitRate.hitCount.getAndAdd(1);
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return null;
    }

    private Integer getKickedKey() {
        HitRate min = Collections.min(serviceCache.values());
        return min.key;
    }
}
