public class Practice5_FleetReconciliation {

    static class BusTicketAccount {

        protected String bookingId;
        protected double ticketFare;

        static String depotName;

        static {
            depotName = "SRM Bus Depot";
        }

        public BusTicketAccount(String bookingId, double ticketFare) {
            if (ticketFare < 0) {
                throw new IllegalArgumentException(
                        "Ticket fare cannot be negative");
            }

            this.bookingId = bookingId;
            this.ticketFare = ticketFare;
        }

        // Provisional constructor using this(...)
        public BusTicketAccount(String bookingId) {
            this(bookingId, 0.0);
        }

        public final double calculatePenalty(int minutesLate) {

            if (minutesLate < 0) {
                throw new IllegalArgumentException(
                        "Minutes late cannot be negative");
            }

            if (minutesLate == 0) {
                return 0.0;
            }

            // Flat-rate penalty: 1% of fare per late minute.
            return ticketFare * 0.01 * minutesLate;
        }

        public void settle(double amount) {
            System.out.println(
                    bookingId + " regular account settled: Rs " + amount);
        }
    }

    static class SleeperAccount extends BusTicketAccount {

        public SleeperAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        @Override
        public void settle(double amount) {
            System.out.println(
                    bookingId + " sleeper account settled: Rs "
                            + amount * 0.9);
        }
    }

    static void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        if (account == null) {
            return;
        }

        try {

            double penalty = account.calculatePenalty(minutesLate);

            if (account instanceof SleeperAccount) {
                System.out.println(
                        account.bookingId
                                + " | Sleeper | Amount: Rs " + amount
                                + " | Penalty: Rs " + penalty);

            } else {
                System.out.println(
                        account.bookingId
                                + " | Regular | Amount: Rs " + amount
                                + " | Penalty: Rs " + penalty);
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    account.bookingId
                            + " | Processing rejected: "
                            + e.getMessage());
        }
    }

    static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null
                || amounts == null
                || minutesLateArray == null) {

            System.out.println("Invalid batch: arrays cannot be null");
            return;
        }

        if (accounts.length != amounts.length
                || accounts.length != minutesLateArray.length) {

            System.out.println(
                    "Invalid batch: array lengths must match");
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalty = 0.0;

        for (int i = 0; i < accounts.length; i++) {

            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {

                double penalty = account.calculatePenalty(minutesLateArray[i]);

                processAccount(
                        account,
                        amounts[i],
                        minutesLateArray[i]);

                grandTotalPenalty += penalty;
                processed++;

                if (account instanceof SleeperAccount) {
                    sleeperCount++;
                } else {
                    regularCount++;
                }

            } catch (IllegalArgumentException e) {

                System.out.println(
                        account.bookingId
                                + " skipped: "
                                + e.getMessage());
            }
        }

        System.out.println();
        System.out.println(
                processed + " processed | "
                        + nullSkipped + " null skipped | "
                        + sleeperCount + " sleeper | "
                        + regularCount + " regular | "
                        + "grand total penalties = Rs "
                        + grandTotalPenalty);
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
                new SleeperAccount("BK001", 2000),
                null,
                new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
                1200,
                900,
                700
        };

        int[] minutesLateArray = {
                10,
                5,
                0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray);
    }
}