package fr.gaeljw.gdomoticz.model.data;

public class SystemMetrics {

    private MemoryInfo jvmMemoryInfo;
    private SystemInfo systemInfo;

    public MemoryInfo getJvmMemoryInfo() {
        return jvmMemoryInfo;
    }

    public void setJvmMemoryInfo(MemoryInfo jvmMemoryInfo) {
        this.jvmMemoryInfo = jvmMemoryInfo;
    }

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }
}
