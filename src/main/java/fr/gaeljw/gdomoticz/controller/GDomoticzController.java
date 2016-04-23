package fr.gaeljw.gdomoticz.controller;

import fr.gaeljw.gdomoticz.model.data.Temperature;
import fr.gaeljw.gdomoticz.service.DomoticzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class GDomoticzController {

    // TODO utiliser JAX RS plutot ?

    @Autowired
    private DomoticzService domoticzService;

    @RequestMapping(method = RequestMethod.GET, value="/temperature")
    public Temperature temperature() {
        return domoticzService.getTemperature("1");
    }

}
