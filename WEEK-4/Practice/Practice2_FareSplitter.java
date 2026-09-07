import java.util.Scanner;

public class Practice2_FareSplitter {

    static class FareSplitter {

        private String tripId;
        private double totalFare;
        private int passengerCount;

        // Main constructor
        public FareSplitter(String tripId, double totalFare, int passengerCount) {

            if (totalFare < 0) {
                throw new IllegalArgumentException("Fare cannot be negative");
            }

            if (passengerCount <= 0) {
                throw new IllegalArgumentException(
                        "Passenger count must be positive");
            }

            this.tripId = tripId;
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        // Constructor chaining using this(...)
        public FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 1);
        }

        // Provisional split
        public FareSplitter(String tripId) {
            this(tripId, 0.0, 2);
        }

        public double[] fareBreakdown() {

            double[] shares = new double[passengerCount];

            if (totalFare == 0) {
                return shares;
            }

            double baseShare = Math.floor((totalFare / passengerCount) * 100) / 100;

            double assigned = baseShare * passengerCount;
            double remainder = Math.round((totalFare - assigned) * 100) / 100.0;

            for (int i = 0; i < passengerCount; i++) {
                shares[i] = baseShare;
            }

            // Give the remaining paisa to the last passenger
            shares[passengerCount - 1] += remainder;

            return shares;
        }

        public boolean isConfirmationOverdue(int confirmed, int expected) {

            if (expected <= 0) {
                return false;
            }

            return confirmed < expected;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter trip ID: ");
        String tripId = sc.nextLine();

        System.out.print("Enter total fare: ");
        double totalFare = sc.nextDouble();

        System.out.print("Enter passenger count: ");
        int passengerCount = sc.nextInt();

        try {

            FareSplitter splitter = new FareSplitter(tripId, totalFare, passengerCount);

            double[] breakdown = splitter.fareBreakdown();

            System.out.print("Fare breakdown: [");

            for (int i = 0; i < breakdown.length; i++) {

                System.out.printf("%.2f", breakdown[i]);

                if (i < breakdown.length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println("]");

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid input: " + e.getMessage());
        }

        sc.close();
    }
}
