import java.util.*;

interface ShippingType {
    double calculateCost(double weight);
}

class StandardShipping implements ShippingType {
    public double calculateCost(double weight) {
        return 40 + (10 * weight);
    }
}

class ExpressShipping implements ShippingType {
    public double calculateCost(double weight) {
        return 80 + (15 * weight);
    }
}

class FragileShipping implements ShippingType {
    public double calculateCost(double weight) {
        return 40 + (10 * weight) + 50;
    }
}

interface NotificationChannel {
    void notify(String message);
}

class SmsChannel implements NotificationChannel {
    public void notify(String message) {
        System.out.println("SMS: " + message);
    }
}

class EmailChannel implements NotificationChannel {
    public void notify(String message) {
        System.out.println("Email: " + message);
    }
}

class Customer {
    String name;
    ArrayList<NotificationChannel> channels = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    void notifyAllChannels(String message) {
        for (NotificationChannel channel : channels)
            channel.notify(message);
    }
}

class Parcel {
    String id;
    double weight;
    String status = "BOOKED";
    Customer customer;
    ShippingType shippingType;

    Parcel(String id, double weight, Customer customer,
            ShippingType shippingType) {
        this.id = id;
        this.weight = weight;
        this.customer = customer;
        this.shippingType = shippingType;
    }

    double getShippingCost() {
        return shippingType.calculateCost(weight);
    }

    boolean updateStatus(String newStatus) {

        if (status.equals("BOOKED") && newStatus.equals("PICKED_UP") ||
                status.equals("PICKED_UP") && newStatus.equals("IN_TRANSIT") ||
                status.equals("IN_TRANSIT") && newStatus.equals("OUT_FOR_DELIVERY") ||
                status.equals("OUT_FOR_DELIVERY") && newStatus.equals("DELIVERED")) {

            status = newStatus;
            customer.notifyAllChannels(
                    "Parcel " + id + " status changed to " + status);
            return true;
        }

        System.out.println("Invalid status change: " + status +
                " -> " + newStatus);
        return false;
    }

    boolean cancel() {
        if (status.equals("BOOKED")) {
            status = "CANCELLED";
            System.out.println("Parcel " + id + " cancelled");
            return true;
        }

        System.out.println("Cancellation failed: parcel already picked up");
        return false;
    }
}

class ParcelService {
    void showDetails(Parcel parcel) {
        System.out.println("Parcel: " + parcel.id);
        System.out.println("Weight: " + parcel.weight + " kg");
        System.out.println("Shipping Cost: Rs." +
                parcel.getShippingCost());
        System.out.println("Status: " + parcel.status);
    }
}

public class Q2_SwiftShipParcelTracker {

    public static void main(String[] args) {

        Customer customer = new Customer("Lakshanya");

        customer.subscribe(new SmsChannel());
        customer.subscribe(new EmailChannel());

        Parcel parcel = new Parcel(
                "P101",
                2,
                customer,
                new ExpressShipping());

        ParcelService service = new ParcelService();

        service.showDetails(parcel);

        System.out.println();

        parcel.updateStatus("PICKED_UP");

        parcel.cancel();

        parcel.updateStatus("IN_TRANSIT");

        // Invalid: cannot skip OUT_FOR_DELIVERY
        parcel.updateStatus("DELIVERED");

        parcel.updateStatus("OUT_FOR_DELIVERY");
        parcel.updateStatus("DELIVERED");
    }
}