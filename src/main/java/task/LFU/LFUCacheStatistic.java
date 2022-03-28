package task.LFU;

import task.model.StringObject;

import java.util.Optional;
import java.util.logging.Logger;

public class LFUCacheStatistic extends LFUCache {

    private static final Logger log = Logger.getLogger(LFUCacheStatistic.class.getName());
    private Integer counterEvictions = 0;
    private Integer counterAdding = 0;
    private Long averageTime = 0L;

    public LFUCacheStatistic (int capacity) {
        super(capacity);
    }

    @Override
    public Optional<StringObject> set(int key, String value) {
        long startTime = 0;
        Optional<StringObject> removedItem = super.set(key, value);
        long entTime = System.nanoTime();
        if (removedItem.isPresent()) {
            counterEvictions++;
            log.info("Removed Item is: " + removedItem.get());
        } else {
            counterAdding++;
            averageTime += (entTime - startTime) / 1000000L;
        }
        return removedItem;
    }

    @Override
    public StringObject get(int key) {
        return super.get(key);
    }

    public void printStatistics() {
        System.out.println("Average time spent for putting new values into the cache: " + averageTime / counterAdding);
        System.out.println("Number of cache evictions: " + counterEvictions);
    }
}
