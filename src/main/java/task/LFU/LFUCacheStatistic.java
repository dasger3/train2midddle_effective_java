package task.LFU;

import org.checkerframework.checker.units.qual.A;
import task.model.StringObject;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class LFUCacheStatistic extends LFUCache {

    private static final Logger log = Logger.getLogger(LFUCacheStatistic.class.getName());
    private final AtomicInteger counterEvictions = new AtomicInteger(0);
    private final AtomicInteger counterAdding = new AtomicInteger(0);
    private final AtomicLong averageTime = new AtomicLong();

    public LFUCacheStatistic (int capacity) {
        super(capacity);
    }

    @Override
    public Optional<StringObject> set(int key, String value) {
        AtomicLong startTime = new AtomicLong(0);
        Optional<StringObject> removedItem = super.set(key, value);
        AtomicLong entTime = new AtomicLong(System.nanoTime());
        if (removedItem.isPresent()) {
            counterEvictions.getAndIncrement();
            log.info("Removed Item is: " + removedItem.get());
        } else {
            counterAdding.getAndIncrement();
            averageTime.addAndGet((entTime.longValue() - startTime.get()) / 1000000L);
        }
        return removedItem;
    }

    @Override
    public StringObject get(int key) {
        return super.get(key);
    }

    public void printStatistics() {
        log.info("Average time spent for putting new values into the cache: " + averageTime.get() / counterAdding.get());
        log.info("Number of cache evictions: " + counterEvictions);
    }
}
