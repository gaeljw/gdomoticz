package fr.gaeljw.gdomoticz.model.data;

public class MemoryInfo {

    long max;
    long allocated;
    long free;

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getAllocated() {
        return allocated;
    }

    public void setAllocated(long allocated) {
        this.allocated = allocated;
    }

    public long getFree() {
        return free;
    }

    public void setFree(long free) {
        this.free = free;
    }

}
