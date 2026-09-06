import java.util.Scanner;

public class Assignment4_ISBNValidator {

    static String normalizeCode(String raw) {

        return raw.trim();
    }

    static String validateAndFormat(String code) {

        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        String publisher = code.substring(0, 3).toUpperCase();

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(publisher.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: ISBN body must contain only digits";
            }
        }

        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        StringBuilder result = new StringBuilder();

        result.append("[")
                .append(publisher)
                .append("] YEAR: 20")
                .append(year.substring(2))
                .append(" | CATALOG: ")
                .append(catalog);

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ISBN code: ");
        String raw = sc.nextLine();

        String code = normalizeCode(raw);

        System.out.println(validateAndFormat(code));

        sc.close();
    }
}
