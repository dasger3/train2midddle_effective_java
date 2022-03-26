package task;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class LFUCache {

    private static final int DEFAULT_MAX_SIZE = 100000;
    private int capacity = DEFAULT_MAX_SIZE;
    private final Map<Integer, HitRate> serviceCache = new ConcurrentHashMap<>();
    private final Map<Integer, StringObject> cache = new ConcurrentHashMap<>();


    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    // @param key, an integer
    // @param value, an integer
    // @return nothing
    public Optional<StringObject> set(int key, String value) {
        StringObject deletedItem = null;
        StringObject v = cache.get(key);
        if (v == null) {
            if (serviceCache.size() == capacity) {
                Integer k = getKickedKey();
                deletedItem = cache.remove(k);
                serviceCache.remove(k);
            }
            serviceCache.put(key, new HitRate(key, 1, System.nanoTime()));
        } else { // Если ключ тот же, только увеличиваем частоту, время обновления и не заменяем
            HitRate hitRate = serviceCache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        cache.put(key, new StringObject(value));
        return (deletedItem == null) ? Optional.empty() : Optional.of(deletedItem);
    }

    public StringObject get(int key) {
        StringObject v = cache.get(key);
        if (v != null) {
            HitRate hitRate = serviceCache.get(key);
            hitRate.hitCount += 1;
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
