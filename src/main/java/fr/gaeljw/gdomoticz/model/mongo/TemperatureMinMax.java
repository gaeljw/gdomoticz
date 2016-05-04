package fr.gaeljw.gdomoticz.model.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class TemperatureMinMax {

    @Field("_id")
    private String nameDevice;
    private List<Point> points;

    public String getNameDevice() {
        return nameDevice;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
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
        private double moy;

        public String getDate() {
            // FIXME hack
            return date + "T00:00:00.000+0200";
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

        public double getMoy() {
            return moy;
        }

        public void setMoy(double moy) {
            this.moy = moy;
        }
    }
}
