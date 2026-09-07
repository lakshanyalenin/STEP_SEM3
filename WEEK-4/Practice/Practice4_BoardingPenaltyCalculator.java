public class Practice4_BoardingPenaltyCalculator {

    static final class BoardingPenaltyCalculator {

        private final double minimumPenaltyPercent;

        public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        public final double calculatePenalty(double ticketFare, int minutesLate) {

            if (ticketFare < 0) {
                throw new IllegalArgumentException(
                        "Ticket fare cannot be negative");
            }

            if (minutesLate < 0) {
                throw new IllegalArgumentException(
                        "Minutes late cannot be negative");
            }

            if (minutesLate == 0) {
                return 0.0;
            }

            int firstTier = Math.min(minutesLate, 5);
            int secondTier = Math.min(Math.max(minutesLate - 5, 0), 10);
            int thirdTier = Math.max(minutesLate - 15, 0);

            double penalty = ticketFare * 0.005 * firstTier
                    + ticketFare * 0.01 * secondTier
                    + ticketFare * 0.02 * thirdTier;

            double minimumFlatPenalty = ticketFare * minimumPenaltyPercent / 100;

            return Math.max(penalty, minimumFlatPenalty);
        }
    }

    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);

        double[] fares = { 1000, 1000, 1000 };
        int[] minutesLate = { 0, 1, 16 };

        for (int i = 0; i < fares.length; i++) {

            double penalty = calculator.calculatePenalty(
                    fares[i], minutesLate[i]);

            System.out.println(
                    "Ticket Fare: Rs " + fares[i]
                            + " | Minutes Late: " + minutesLate[i]
                            + " | Penalty: Rs " + penalty);
        }
    }
}
