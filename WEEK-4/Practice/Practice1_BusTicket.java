import java.util.HashSet;

public class Practice1_BusTicket {

    static class BusTicket {

        private String passengerName;
        private String destination;
        private boolean checkedIn;

        public BusTicket(String passengerName, String destination) {

            if (passengerName == null || passengerName.isBlank()) {
                throw new IllegalArgumentException("Invalid passenger name");
            }

            if (destination == null || destination.isBlank()) {
                throw new IllegalArgumentException("Invalid destination");
            }

            if (passengerName.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Passenger name cannot contain digits");
            }

            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.checkedIn = false;
        }

        public void markCheckedIn() {

            if (!checkedIn) {
                checkedIn = true;
            }
        }

        String getBookingKey() {
            return passengerName.toLowerCase()
                    + "|"
                    + destination.toLowerCase();
        }
    }

    static void processBatch(String[][] rawBookings) {

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        HashSet<String> acceptedBookings = new HashSet<>();

        for (String[] booking : rawBookings) {

            try {

                if (booking == null || booking.length != 2) {
                    rejected++;
                    continue;
                }

                BusTicket ticket = new BusTicket(booking[0], booking[1]);

                String key = ticket.getBookingKey();

                if (acceptedBookings.contains(key)) {
                    duplicates++;
                } else {
                    acceptedBookings.add(key);
                    valid++;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid
                + " | Rejected: " + rejected
                + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {

        String[][] rawBookings = {
                { "Divya", "Chennai" },
                { "", "Bangalore" },
                { "Ravi123", "Pune" },
                { "Divya", "Chennai" },
                { " ", " " }
        };

        processBatch(rawBookings);
    }
}