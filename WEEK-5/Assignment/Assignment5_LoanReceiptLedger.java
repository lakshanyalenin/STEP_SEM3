public class Assignment5_LoanReceiptLedger {

    static {
        System.out.println("Nightly circulation ledger initialized.");
    }

    // The prompt requires both final and subclassing.
    // A final class cannot be extended, so the class is kept
    // non-final while all its fields remain final.
    static class LoanReceipt {

        private final String bookId;
        private final String memberId;
        private final int loanDays;

        public LoanReceipt(
                String bookId,
                String memberId,
                int loanDays) {

            if (bookId == null
                    || !bookId.matches("BK-\\d{3}")) {
                throw new IllegalArgumentException(
                        "Invalid book ID.");
            }

            this.bookId = bookId;
            this.memberId = memberId;
            this.loanDays = loanDays;
        }

        public String getBookId() {
            return bookId;
        }

        public String getMemberId() {
            return memberId;
        }

        public int getLoanDays() {
            return loanDays;
        }

        public LoanReceipt withCorrectedBookId(
                String correctedBookId) {

            return new LoanReceipt(
                    correctedBookId,
                    memberId,
                    loanDays);
        }
    }

    static class ReferenceOnlyLoanReceipt
            extends LoanReceipt {

        public ReferenceOnlyLoanReceipt(
                String bookId,
                String memberId,
                int loanDays) {

            super(bookId, memberId, loanDays);
        }
    }

    static void processBatch(LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        System.out.println(
                processed + " processed | "
                        + nullSkipped + " null skipped | "
                        + referenceOnly + " reference-only | "
                        + regular + " regular");
    }

    public static void main(String[] args) {

        LoanReceipt regular = new LoanReceipt(
                "BK-101",
                "LB94",
                7);

        LoanReceipt reference = new ReferenceOnlyLoanReceipt(
                "BK-102",
                "LB95",
                5);

        LoanReceipt[] receipts = {
                regular,
                null,
                reference
        };

        processBatch(receipts);

        LoanReceipt corrected = regular.withCorrectedBookId("BK-999");

        System.out.println(
                "Original book ID: "
                        + regular.getBookId());

        System.out.println(
                "Corrected book ID: "
                        + corrected.getBookId());
    }
}