public class Assignment4_SurgeFeeCalculator {

    static final class SurgeFeeCalculator {
        private final double minimumSurgePercent;

        public SurgeFeeCalculator(double minimumSurgePercent) {
            this.minimumSurgePercent = minimumSurgePercent;
        }

        public final double calculateSurgeFee(double orderValue, int delayMinutes) {

            if (orderValue < 0 || delayMinutes < 0) {
                throw new IllegalArgumentException(
                        "Order value and delay cannot be negative");
            }

            if (delayMinutes == 0) {
                return 0.0;
            }

            double surgeFee = 0.0;

            if (delayMinutes >= 1) {
                int minutes = Math.min(delayMinutes, 5);
                surgeFee += orderValue * 0.005 * minutes;
            }

            if (delayMinutes >= 6) {
                int minutes = Math.min(delayMinutes, 15) - 5;
                surgeFee += orderValue * 0.01 * minutes;
            }

            if (delayMinutes >= 16) {
                int minutes = delayMinutes - 15;
                surgeFee += orderValue * 0.02 * minutes;
            }

            double minimumFee = orderValue * minimumSurgePercent / 100.0;

            return Math.max(surgeFee, minimumFee);
        }
    }

    public static void main(String[] args) {

        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);

        System.out.println("₹500, 0 min: ₹"
                + calculator.calculateSurgeFee(500, 0));

        System.out.println("₹500, 1 min: ₹"
                + calculator.calculateSurgeFee(500, 1));

        System.out.println("₹500, 16 min: ₹"
                + calculator.calculateSurgeFee(500, 16));
    }
}