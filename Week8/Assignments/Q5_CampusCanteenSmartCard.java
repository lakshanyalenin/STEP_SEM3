import java.util.*;

interface PricingPlan {
    double getPrice(double price);
}

class DayScholarPlan implements PricingPlan {
    public double getPrice(double price) {
        return price;
    }
}

class HostellerPlan implements PricingPlan {
    public double getPrice(double price) {
        return price * 0.90;
    }
}

class StaffPlan implements PricingPlan {
    public double getPrice(double price) {
        return price * 0.80;
    }
}

class Transaction {
    String description;
    double amount;

    Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    void show() {
        System.out.printf("%s : %+.2f%n", description, amount);
    }
}

class SmartCard {
    String cardNumber;
    double balance = 0;
    boolean blocked = false;
    PricingPlan pricingPlan;

    ArrayList<Transaction> transactions = new ArrayList<>();
    HashMap<String, Double> purchases = new HashMap<>();

    SmartCard(String cardNumber, PricingPlan pricingPlan) {
        this.cardNumber = cardNumber;
        this.pricingPlan = pricingPlan;
    }

    void topUp(double amount) {

        if (blocked) {
            System.out.println("Top-up rejected: card is blocked");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up rejected: minimum is Rs.100");
            return;
        }

        if (balance + amount > 5000) {
            System.out.println("Top-up rejected: maximum balance is Rs.5000");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Top-up", amount));

        System.out.println("Top-up successful: Rs." + amount);
    }

    void purchase(String item, double originalPrice) {

        if (blocked) {
            System.out.println("Purchase rejected: card is blocked");
            return;
        }

        double chargedPrice = pricingPlan.getPrice(originalPrice);

        if (chargedPrice > balance) {
            System.out.println("Purchase failed: insufficient balance");
            return;
        }

        balance -= chargedPrice;

        transactions.add(
                new Transaction(item, -chargedPrice));

        purchases.put(item, chargedPrice);

        System.out.printf(
                "%s purchased for Rs.%.0f%n",
                item, chargedPrice);
    }

    void refund(String item) {

        if (blocked) {
            System.out.println("Refund rejected: card is blocked");
            return;
        }

        if (!purchases.containsKey(item)) {
            System.out.println("Refund rejected: already refunded or not found");
            return;
        }

        double amount = purchases.remove(item);

        balance += amount;

        transactions.add(
                new Transaction("Refund " + item, amount));

        System.out.printf(
                "Refund successful: Rs.%.0f%n",
                amount);
    }

    void block() {
        blocked = true;
        System.out.println("Card blocked");
    }

    void unblock() {
        blocked = false;
        System.out.println("Card unblocked");
    }

    void showBalance() {
        System.out.printf("Balance: Rs.%.0f%n", balance);
    }

    void showStatement() {

        System.out.println("\nMini Statement:");

        for (Transaction transaction : transactions) {
            transaction.show();
        }

        System.out.printf("Current Balance: Rs.%.0f%n", balance);
    }
}

public class Q5_CampusCanteenSmartCard {

    public static void main(String[] args) {

        SmartCard card = new SmartCard(
                "C-2045",
                new HostellerPlan());

        card.topUp(500);

        card.purchase("Veg Thali", 120);

        card.showBalance();

        card.purchase("Cold Coffee", 60);

        card.showBalance();

        card.purchase("Large Meal", 400);

        card.refund("Veg Thali");

        card.showBalance();

        // Second refund should fail
        card.refund("Veg Thali");

        card.showStatement();
    }
}