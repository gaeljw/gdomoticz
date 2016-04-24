package fr.gaeljw.gdomoticz.service;

import fr.gaeljw.gdomoticz.model.data.MemoryInfo;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    public MemoryInfo getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setMax(runtime.maxMemory());
        memoryInfo.setAllocated(runtime.totalMemory());
        memoryInfo.setFree(runtime.freeMemory());
        return memoryInfo;
    }

}
