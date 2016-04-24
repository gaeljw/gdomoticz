package fr.gaeljw.gdomoticz.controller;

import fr.gaeljw.gdomoticz.model.data.MemoryInfo;
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

    @RequestMapping(method = RequestMethod.GET, value = "/memory")
    public MemoryInfo memory() {
        return systemService.getMemoryInfo();
    }

}
