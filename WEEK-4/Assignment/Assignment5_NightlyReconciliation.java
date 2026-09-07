public class Assignment5_NightlyReconciliation {

    static class DeliveryAccount {
        protected String studentId;
        protected double balance;

        static {
            System.out.println("Delivery reconciliation system initialized.");
        }

        public DeliveryAccount(String studentId, double balance) {
            this.studentId = studentId;
            this.balance = balance;
        }

        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        public double calculateSurgeFee(double orderValue, int delayMinutes) {
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

            return Math.max(surgeFee, orderValue * 0.01);
        }

        public double settleSurgeFee(double orderValue, int delayMinutes) {
            return calculateSurgeFee(orderValue, delayMinutes);
        }
    }

    static class PremiumDeliveryAccount extends DeliveryAccount {

        public PremiumDeliveryAccount(String studentId, double balance) {
            super(studentId, balance);
        }

        @Override
        public double settleSurgeFee(double orderValue, int delayMinutes) {
            return calculateSurgeFee(orderValue, delayMinutes) * 0.5;
        }
    }

    static void processBatch(
            DeliveryAccount[] accounts,
            double[] orderValues,
            int[] delays) {

        if (accounts.length != orderValues.length
                || accounts.length != delays.length) {
            throw new IllegalArgumentException(
                    "Batch arrays must have the same length");
        }

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            double fee;

            if (accounts[i] instanceof PremiumDeliveryAccount) {
                premiumCount++;
                fee = accounts[i].settleSurgeFee(
                        orderValues[i], delays[i]);
            } else {
                regularCount++;
                fee = accounts[i].settleSurgeFee(
                        orderValues[i], delays[i]);
            }

            grandTotal += fee;
            processed++;
        }

        System.out.println("Processed: " + processed);
        System.out.println("Null skipped: " + nullSkipped);
        System.out.println("Premium: " + premiumCount);
        System.out.println("Regular: " + regularCount);
        System.out.println("Grand Total Surge Fees: ₹" + grandTotal);
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
                new PremiumDeliveryAccount("STU001", 500),
                null,
                new DeliveryAccount("STU002", 300)
        };

        double[] orderValues = { 500, 400, 300 };
        int[] delays = { 10, 5, 0 };

        processBatch(accounts, orderValues, delays);
    }
}