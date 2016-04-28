package fr.gaeljw.gdomoticz.controller;

import fr.gaeljw.gdomoticz.model.data.Temperature;
import fr.gaeljw.gdomoticz.model.mongo.TemperatureAggregation;
import fr.gaeljw.gdomoticz.model.mongo.TemperatureMinMax;
import fr.gaeljw.gdomoticz.service.DomoticzService;
import fr.gaeljw.gdomoticz.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class GDomoticzController {

    // TODO utiliser JAX RS plutot ?

    @Autowired
    private DomoticzService domoticzService;
    @Autowired
    private MongoService mongoService;

    // FIXME enlever
    @RequestMapping(method = RequestMethod.GET, value = "/temperature")
    public Temperature temperature() {
        return domoticzService.getTemperature("1");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/temperatures")
    public List<Temperature> temperatures() {
        return domoticzService.getTemperatures();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/history/temperatures")
    public List<TemperatureAggregation> historiqueTemperatures() {
        return mongoService.getLastDayTemperatures();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/history/temperatures/month")
    public List<TemperatureMinMax> monthHistoryTemperatures() {
        return mongoService.getLastMonthTemperatures();
    }

}
