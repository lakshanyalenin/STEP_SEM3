import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

abstract class Room {
    protected String roomNumber;
    protected String category;

    public Room(String roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
    }

    public abstract double calculatePrice(long days);
}

class StandardRoom extends Room {

    public StandardRoom(String roomNumber) {
        super(roomNumber, "Standard");
    }

    public double calculatePrice(long days) {
        return days * 150;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String roomNumber) {
        super(roomNumber, "Deluxe");
    }

    public double calculatePrice(long days) {
        return days * 200;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Reservation {
    Room room;
    Customer customer;
    LocalDate startDate;
    LocalDate endDate;
    boolean active = true;
    double price;

    public Reservation(Room room, Customer customer,
            LocalDate startDate, LocalDate endDate) {

        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        this.price = room.calculatePrice(days);
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return start.isBefore(endDate) && end.isAfter(startDate);
    }

    public void cancel() {
        active = false;
    }
}

class BookingManager {

    ArrayList<Reservation> reservations = new ArrayList<>();

    public void bookRoom(Room room, Customer customer,
            LocalDate start, LocalDate end) {

        for (Reservation r : reservations) {
            if (r.active && r.room == room && r.overlaps(start, end)) {
                System.out.println("Booking failed: " + room.roomNumber
                        + " is not available for " + start + " to " + end);
                return;
            }
        }

        Reservation reservation = new Reservation(room, customer, start, end);

        reservations.add(reservation);

        System.out.printf("%s %s booked from %s to %s. Total price: $%.2f%n",
                room.category, room.roomNumber,
                start, end, reservation.price);
    }

    public void cancelReservation(Room room) {

        for (Reservation r : reservations) {
            if (r.room == room && r.active) {
                r.cancel();

                System.out.println("Reservation for "
                        + room.category + " Room "
                        + room.roomNumber
                        + " cancelled successfully.");
                return;
            }
        }

        System.out.println("No active reservation found.");
    }
}

public class Q3_HotelBookingSystem {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        Room deluxeRoom = new DeluxeRoom("101");
        Room standardRoom = new StandardRoom("205");

        BookingManager manager = new BookingManager();

        manager.bookRoom(
                deluxeRoom,
                customer,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5));

        manager.bookRoom(
                standardRoom,
                customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.bookRoom(
                deluxeRoom,
                customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.cancelReservation(deluxeRoom);
    }
}