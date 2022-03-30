package task.LRU;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LRUCache cache = new LRUCache(4);
        cache.set(1, "first");
        cache.set(2, "second");
        cache.set(3, "third");
        cache.set(4, "fourth");
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        cache.set(5, "fifth");
        cache.printCache();
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        cache.set(3, "third1");
        cache.printCache();
        cache.set(4, "fourth1");
        cache.printCache();
        cache.set(5, "fifth1");
        cache.printCache();
        cache.printStatistics();
    }


}
