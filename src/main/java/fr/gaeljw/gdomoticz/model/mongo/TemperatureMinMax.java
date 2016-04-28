package fr.gaeljw.gdomoticz.model.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class TemperatureMinMax {

    @Field("_id")
    private String idDevice;
    private List<Point> points;

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    private static class Point {

        private String date;
        private double min;
        private double max;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

    }
}
