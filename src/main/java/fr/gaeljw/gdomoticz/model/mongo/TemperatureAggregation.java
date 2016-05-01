package fr.gaeljw.gdomoticz.model.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

public class TemperatureAggregation {

    @Field("_id")
    private String nameDevice;
    private List<TemperaturePoint> temperatures;

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public List<TemperaturePoint> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TemperaturePoint> temperatures) {
        this.temperatures = temperatures;
    }

    public static class TemperaturePoint {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        private Date date;
        private double temperature;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }
}
