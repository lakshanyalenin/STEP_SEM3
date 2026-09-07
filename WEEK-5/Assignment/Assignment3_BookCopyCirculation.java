public class Assignment3_BookCopyCirculation {

    static class BookInventory {

        private int copiesTotal;
        private int copiesAvailable;

        public BookInventory(int copiesTotal) {

            if (copiesTotal <= 0) {
                throw new IllegalArgumentException(
                        "Total copies must be greater than 0.");
            }

            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        public void checkOut() {

            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        public void checkIn() {

            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        public int getCopiesAvailable() {
            return copiesAvailable;
        }
    }

    public static void main(String[] args) {

        BookInventory book = new BookInventory(3);

        book.checkOut();
        book.checkOut();
        book.checkOut();
        book.checkOut();

        System.out.println(
                "Available after 4 checkouts: "
                        + book.getCopiesAvailable());

        book.checkIn();
        book.checkIn();
        book.checkIn();
        book.checkIn();

        System.out.println(
                "Available after 4 checkins: "
                        + book.getCopiesAvailable());
    }
}