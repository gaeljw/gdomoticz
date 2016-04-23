package fr.gaeljw.gdomoticz.model.domoticz.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomoticzResponse {

    private String status;
    private DomoticzResult[] result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DomoticzResult[] getResult() {
        return result;
    }

    public void setResult(DomoticzResult[] result) {
        this.result = result;
    }
}
