package task.LFU;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] as) {
        LFUCacheStatistic cache = new LFUCacheStatistic(3);
        cache.set(1, "first");
        cache.set(2, "second");

        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));

        cache.set(3, "third");
        cache.set(4, "fourth");

        System.out.println(cache.get(3));
        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        System.out.println(cache.get(4));

        cache.printStatistics();
    }
}
