public class Assignment3_CanteenRanking {

    static class Canteen {

        private String canteenCode;
        private String canteenName;
        private int trustScore;

        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        // Constructor chaining with default trust score
        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }

        public int compareTo(Canteen other) {

            // Higher trust score comes first
            if (this.trustScore != other.trustScore) {
                return other.trustScore - this.trustScore;
            }

            // If scores are tied, compare codes ignoring case
            int codeComparison = this.canteenCode.compareToIgnoreCase(other.canteenCode);

            if (codeComparison != 0) {
                return codeComparison;
            }

            // If codes are also tied, shorter name comes first
            return this.canteenName.length() - other.canteenName.length();
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        // Bubble sort — stable when equal elements are not swapped
        for (int i = 0; i < canteens.length - 1; i++) {

            for (int j = 0; j < canteens.length - 1 - i; j++) {

                if (canteens[j].compareTo(canteens[j + 1]) > 0) {

                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }

        return canteens;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(canteens);

        System.out.println("Ranked canteens:");

        for (Canteen canteen : ranked) {
            System.out.println(canteen.canteenCode);
        }
    }
}