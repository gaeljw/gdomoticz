package fr.gaeljw.gdomoticz.controller;

import fr.gaeljw.gdomoticz.model.data.SystemMetrics;
import fr.gaeljw.gdomoticz.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(method = RequestMethod.GET, value = "/metrics")
    public SystemMetrics metrics() {
        SystemMetrics systemMetrics = new SystemMetrics();
        systemMetrics.setJvmMemoryInfo(systemService.getMemoryInfo());
        systemMetrics.setSystemInfo(systemService.getSystemInfo());
        return systemMetrics;
    }

}
