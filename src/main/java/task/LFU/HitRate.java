package task.LFU;

import java.util.concurrent.atomic.AtomicInteger;

public class HitRate implements Comparable<HitRate> {
    Integer key;
    AtomicInteger hitCount; // количество попаданий
    Long lastTime; // Время последнего обращения

    public HitRate(Integer key, AtomicInteger hitCount, Long lastTime) {
        this.key = key;
        this.hitCount = hitCount;
        this.lastTime = lastTime;
    }

    public int compareTo(HitRate o) {
        int hr = Integer.compare(hitCount.get(), o.hitCount.get());
        return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
    }
}
