public class Practice5_FeeHostelSystem {

    // Base fee account
    static class FeeAccount {

        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            } else {
                System.out.println("Payment rejected.");
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    // Hostel fee account inherits from FeeAccount
    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    // Hostel room class
    static class HostelRoom {

        String roomNo;
        int beds;
        int occupied;

        HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        void allot(String name) {
            if (occupied < beds) {
                occupied++;
                System.out.println(name + " allotted to " + roomNo);
            }
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String name) {

        HostelRoom availableRoom = findAvailableRoom(rooms);

        if (availableRoom != null) {
            availableRoom.allot(name);
        } else {
            System.out.println("No rooms available for " + name);
        }
    }

    // SrmStudent uses composition:
    // A student HAS a fee account and MAY HAVE a hostel room.
    static class SrmStudent {

        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        static int totalStudents = 0;

        SrmStudent(String name, String regNo,
                HostelFeeAccount feeAccount, HostelRoom room) {

            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = room;

            totalStudents++;
        }

        void fullStatus() {

            String roomInfo;

            if (room != null) {
                roomInfo = room.roomNo;
            } else {
                roomInfo = "unallotted";
            }

            System.out.println(
                    name + " | Fee due: Rs "
                            + feeAccount.getDue()
                            + " | Room: " + roomInfo);
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms = {
                new HostelRoom("C-214", 1, 0),
                new HostelRoom("C-507", 1, 0)
        };

        HostelFeeAccount raviFee = new HostelFeeAccount("RA101", 200000, 0);

        HostelFeeAccount anithaFee = new HostelFeeAccount("RA102", 200000, 0);

        HostelFeeAccount karthikFee = new HostelFeeAccount("RA103", 200000, 0);

        // Valid payment
        raviFee.pay(60000);

        // Payment using two installments
        anithaFee.payInTwoInstallments(20000);

        // Invalid payment
        karthikFee.pay(-5000);

        HostelRoom raviRoom = findAvailableRoom(rooms);
        if (raviRoom != null) {
            raviRoom.allot("Ravi");
        }

        HostelRoom anithaRoom = findAvailableRoom(rooms);
        if (anithaRoom != null) {
            anithaRoom.allot("Anitha");
        }

        SrmStudent ravi = new SrmStudent("Ravi", "RA101", raviFee, raviRoom);

        SrmStudent anitha = new SrmStudent("Anitha", "RA102", anithaFee, anithaRoom);

        // Karthik has no room assigned.
        SrmStudent karthik = new SrmStudent("Karthik", "RA103", karthikFee, null);

        ravi.fullStatus();
        anitha.fullStatus();
        karthik.fullStatus();

        System.out.println(
                "Total students: " + SrmStudent.totalStudents);
    }
}