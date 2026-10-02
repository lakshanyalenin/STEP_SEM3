import java.util.ArrayList;

interface IPaymentMethod {
    boolean pay(double amount);

    String getName();
}

class CreditCardPayment implements IPaymentMethod {

    public boolean pay(double amount) {
        System.out.printf("Payment via Credit Card successful. Amount: $%.2f%n", amount);
        return true;
    }

    public String getName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {

    public boolean pay(double amount) {
        System.out.println("Payment via Digital Wallet failed.");
        return false;
    }

    public String getName() {
        return "Digital Wallet";
    }
}

class FoodItem {
    String name;
    double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class LineItem {
    FoodItem item;
    int quantity;

    public LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotal() {
        return item.price * quantity;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }

    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class Order {
    private ArrayList<LineItem> items = new ArrayList<>();
    private Customer customer;
    private int orderNumber;
    private String status = "Pending Payment";

    public Order(int orderNumber, Customer customer) {
        this.orderNumber = orderNumber;
        this.customer = customer;

        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        items.add(new LineItem(item, quantity));

        System.out.println("Added " + item.name
                + " (Qty " + quantity + ")");
    }

    public void placeOrder(IPaymentMethod paymentMethod) {

        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item.");
            return;
        }

        System.out.println("Order placed successfully.");

        double total = 0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        boolean paymentSuccessful = paymentMethod.pay(total);

        if (paymentSuccessful) {
            status = "Paid";

            System.out.println("Order status: " + status);

            customer.notifyCustomer(
                    "Order #" + orderNumber + " placed and paid.");
        } else {
            status = "Pending Payment";

            System.out.println("Order status: " + status);

            customer.notifyCustomer(
                    "Order #" + orderNumber
                            + " placed, awaiting payment.");
        }
    }
}

public class Q5_FoodOrderPaymentSystem {

    public static void main(String[] args) {

        Customer customer = new Customer("John");

        FoodItem pizza = new FoodItem("Pizza", 10);
        FoodItem soda = new FoodItem("Soda", 5);
        FoodItem burger = new FoodItem("Burger", 8);

        // First order
        Order order1 = new Order(123, customer);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        // Attempt with empty cart is demonstrated separately
        Order emptyOrder = new Order(122, customer);
        emptyOrder.placeOrder(new CreditCardPayment());

        order1.placeOrder(new CreditCardPayment());

        // Second order
        Order order2 = new Order(124, customer);

        order2.addItem(burger, 1);

        order2.placeOrder(new DigitalWalletPayment());
    }
}