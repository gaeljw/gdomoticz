package fr.gaeljw.gdomoticz.service;

import fr.gaeljw.gdomoticz.model.data.Temperature;
import fr.gaeljw.gdomoticz.model.domoticz.api.DomoticzResponse;
import fr.gaeljw.gdomoticz.model.domoticz.api.DomoticzResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DomoticzService {

    // Faire une implem call direct + une implem mongo

    @Value("${domoticz.api.url}")
    private String domoticzApiUrl;

    public Temperature getTemperature(String idDevice) {
        RestTemplate restTemplate = new RestTemplate();
        // TODO parametrer URL complete
        Map<String, Object> parametres = new HashMap<>();
        parametres.put("type", "devices");
        parametres.put("rid", idDevice);
        DomoticzResponse response = restTemplate.getForObject(domoticzApiUrl + "?type={type}&rid={rid}", DomoticzResponse.class, parametres);

        // TODO ENUM Status
        if (response != null && response.getStatus().equals("OK") && response.getResult().length > 0) {
            DomoticzResult result = response.getResult()[0];
            Temperature t = new Temperature();
            t.setTemperature(result.getTemp());
            t.setLastUpdate(result.getLastUpdate());
            return t;
        } else {
            // TODO log
            return null;
        }

    }

}
