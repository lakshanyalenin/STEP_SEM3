import java.util.Scanner;

public class Practice5_BankTransactionReference {

    static String normalizeReference(String raw) {
        raw = raw.trim();

        if (raw.length() < 3) {
            return raw;
        }

        return raw.substring(0, 3).toUpperCase() + raw.substring(3);
    }

    static String validateAndFormat(String reference) {

        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        String bankCode = reference.substring(0, 3);

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: transaction body must contain only digits";
            }
        }

        String date = reference.substring(3, 9);
        String sequence = reference.substring(9, 14);

        return "[" + bankCode + "] DATE: "
                + date.substring(0, 2) + "/"
                + date.substring(2, 4) + "/"
                + date.substring(4, 6)
                + " | SEQ: " + sequence;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");
        String raw = sc.nextLine();

        String reference = normalizeReference(raw);

        System.out.println(validateAndFormat(reference));

        sc.close();
    }
}