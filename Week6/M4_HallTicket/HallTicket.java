class HallTicket {

    String studentName;
    int seatNumber;

    // Constructor
    HallTicket(String studentName, int seatNumber) {
        this.studentName = studentName;
        this.seatNumber = seatNumber;
    }

    public static void main(String[] args) {

        // Create one HallTicket object
        HallTicket priya = new HallTicket("Priya", 0);

        // copy refers to the same object
        HallTicket copy = priya;

        // Change seat number using copy
        copy.seatNumber = 45;

        // Create a separate object
        HallTicket separate = new HallTicket("Priya", 45);

        // Print results
        System.out.println("Priya's seatNumber (via first variable):");
        System.out.println(priya.seatNumber);

        System.out.println("copy == priya: " + (copy == priya));
        System.out.println("separate == priya: " + (separate == priya));
    }
}