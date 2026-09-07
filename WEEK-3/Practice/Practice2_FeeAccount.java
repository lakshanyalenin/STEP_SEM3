public class Practice2_FeeAccount {

    static class FeeAccount {

        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee, double amountPaid) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = amountPaid;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            } else {
                System.out.println("Payment rejected.");
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
            super(regNo, totalFee, amountPaid);
        }

        void payInTwoInstallments(double amount) {
            pay(amount / 2);
            pay(amount / 2);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {

        private double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee,
                double amountPaid, double scholarshipPercent) {
            super(regNo, totalFee, amountPaid);
            this.scholarshipPercent = scholarshipPercent;
        }

        double effectiveDue() {
            double due = getDue();
            return due - (due * scholarshipPercent / 100);
        }
    }

    public static void main(String[] args) {

        FeeAccount plainAccount = new FeeAccount("RA101", 150000, 0);

        HostelFeeAccount hostelAccount = new HostelFeeAccount("RA102", 200000, 0);

        ScholarshipFeeAccount scholarshipAccount = new ScholarshipFeeAccount("RA103", 180000, 0, 20);

        plainAccount.pay(150000);

        hostelAccount.pay(60000);

        if (hostelAccount instanceof HostelFeeAccount) {
            hostelAccount.payInTwoInstallments(0);
        }

        if (scholarshipAccount instanceof ScholarshipFeeAccount) {
            System.out.println("Scholarship effective due: Rs "
                    + scholarshipAccount.effectiveDue());
        }

        System.out.println("Plain account due: Rs "
                + plainAccount.getDue());

        System.out.println("Hostel account due: Rs "
                + hostelAccount.getDue());
    }
}