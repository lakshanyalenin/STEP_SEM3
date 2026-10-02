abstract class Vehicle {
    protected String name;
    protected boolean available = true;

    public Vehicle(String name) {
        this.name = name;
    }

    public abstract double calculateCharge(int days);

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        available = false;
    }

    public void returnVehicle() {
        available = true;
    }
}

class StandardCar extends Vehicle {

    public StandardCar(String name) {
        super(name);
    }

    public double calculateCharge(int days) {
        return days * 50;
    }
}

class LuxuryCar extends Vehicle {

    public LuxuryCar(String name) {
        super(name);
    }

    public double calculateCharge(int days) {
        return days * 100;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }
}

class Rental {
    Vehicle vehicle;
    Customer customer;
    int days;
    double amount;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.amount = vehicle.calculateCharge(days);
    }

    public void display() {
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.name, days, amount);
    }
}

class RentalService {

    public void rentVehicle(Vehicle vehicle, Customer customer, int days) {

        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.name + " is not available.");
            return;
        }

        vehicle.rent();

        Rental rental = new Rental(vehicle, customer, days);

        rental.display();
    }

    public void returnVehicle(Vehicle vehicle) {

        vehicle.returnVehicle();

        System.out.println(vehicle.name + " returned. Now available.");
    }
}

public class Q2_VehicleRentalSystem {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        Vehicle luxuryCar = new LuxuryCar("Luxury Car A");
        Vehicle standardCar = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        service.rentVehicle(luxuryCar, customer, 3);

        service.rentVehicle(standardCar, customer, 5);

        service.returnVehicle(luxuryCar);
    }
}