public class Practice4_InstanceStatic {

    // Broken version: all fields are static.
    static class BrokenSrmStudent {

        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    // Fixed version.
    static class SrmStudent {

        String name;
        String regNo;
        int attendance;

        static String university = "SRMIST";
        static int admissionCount = 0;

        SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;

            admissionCount++;
            this.regNo = "RA2311003010" + (10 + admissionCount);
        }

        void printIdCard() {
            System.out.println(name + " | " + regNo);
        }

        static void printTotalAdmissions() {
            System.out.println("Students admitted so far: " + admissionCount);
        }
    }

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenSrmStudent student1 = new BrokenSrmStudent("Ravi", "RA231100301011", 82);

        BrokenSrmStudent student2 = new BrokenSrmStudent("Meera", "RA231100301012", 74);

        System.out.println(student1.name);
        System.out.println(student2.name);

        System.out.println();

        System.out.println("Fixed version:");

        SrmStudent fixedStudent1 = new SrmStudent("Ravi", 82);

        SrmStudent fixedStudent2 = new SrmStudent("Meera", 74);

        fixedStudent1.printIdCard();
        fixedStudent2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}