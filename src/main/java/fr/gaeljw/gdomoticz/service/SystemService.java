package fr.gaeljw.gdomoticz.service;

import com.sun.management.OperatingSystemMXBean;
import fr.gaeljw.gdomoticz.model.data.MemoryInfo;
import fr.gaeljw.gdomoticz.model.data.SystemInfo;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

@Service
public class SystemService {

    /**
     * Get JVM memory info.
     *
     * @return
     */
    public MemoryInfo getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setMax(runtime.maxMemory());
        memoryInfo.setAllocated(runtime.totalMemory());
        memoryInfo.setFree(runtime.freeMemory());
        return memoryInfo;
    }

    /**
     * Get system memory & CPU info.
     *
     * @return
     */
    public SystemInfo getSystemInfo() {
        OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setCpuLoad(mxBean.getSystemCpuLoad());
        systemInfo.setCpuLoadAverage(mxBean.getSystemLoadAverage());
        systemInfo.setFreeMemory(mxBean.getFreePhysicalMemorySize());
        systemInfo.setTotalMemory(mxBean.getTotalPhysicalMemorySize());
        systemInfo.setFreeSwap(mxBean.getFreeSwapSpaceSize());
        systemInfo.setTotalSwap(mxBean.getTotalSwapSpaceSize());
        return systemInfo;
    }

}
