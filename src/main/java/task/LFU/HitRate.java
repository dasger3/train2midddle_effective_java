package task.LFU;

public class HitRate implements Comparable<HitRate> {
    Integer key;
    Integer hitCount; // количество попаданий
    Long lastTime; // Время последнего обращения

    public HitRate(Integer key, Integer hitCount, Long lastTime) {
        this.key = key;
        this.hitCount = hitCount;
        this.lastTime = lastTime;
    }

    public int compareTo(HitRate o) {
        int hr = hitCount.compareTo(o.hitCount);
        return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
    }
}
