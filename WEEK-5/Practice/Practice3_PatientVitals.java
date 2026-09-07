import java.util.Arrays;

public class Practice3_PatientVitals {

    static class PatientVitals {

        private double[] readings;

        public PatientVitals(double[] initialReadings) {
            readings = new double[0];

            if (initialReadings != null) {
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        public void recordReading(double reading) {

            if (reading <= 0 || reading > 45) {
                return;
            }

            double[] newReadings = Arrays.copyOf(readings, readings.length + 1);

            newReadings[newReadings.length - 1] = reading;
            readings = newReadings;
        }

        public double getAverage() {

            if (readings.length == 0) {
                return 0.0;
            }

            double total = 0.0;

            for (double reading : readings) {
                total += reading;
            }

            return total / readings.length;
        }

        public double[] getAllReadings() {
            return Arrays.copyOf(readings, readings.length);
        }
    }

    public static void main(String[] args) {

        PatientVitals v = new PatientVitals(
                new double[] { 36.5, -2, 37.1 });

        System.out.println(
                "Readings: " + Arrays.toString(v.getAllReadings()));

        System.out.println(
                "Average: " + v.getAverage());

        double[] copy = v.getAllReadings();
        copy[0] = 999;

        System.out.println(
                "After modifying copy: "
                        + Arrays.toString(v.getAllReadings()));

        v.recordReading(38.2);
        v.recordReading(-5);

        System.out.println(
                "Final readings: "
                        + Arrays.toString(v.getAllReadings()));
    }
}