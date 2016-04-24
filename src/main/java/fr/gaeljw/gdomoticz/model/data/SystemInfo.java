package fr.gaeljw.gdomoticz.model.data;

public class SystemInfo {

    private long freeMemory;
    private long totalMemory;
    private long freeSwap;
    private long totalSwap;
    private double cpuLoad;
    private double cpuLoadAverage;

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getFreeSwap() {
        return freeSwap;
    }

    public void setFreeSwap(long freeSwap) {
        this.freeSwap = freeSwap;
    }

    public long getTotalSwap() {
        return totalSwap;
    }

    public void setTotalSwap(long totalSwap) {
        this.totalSwap = totalSwap;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public double getCpuLoadAverage() {
        return cpuLoadAverage;
    }

    public void setCpuLoadAverage(double cpuLoadAverage) {
        this.cpuLoadAverage = cpuLoadAverage;
    }
}
