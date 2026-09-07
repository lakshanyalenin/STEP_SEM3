public class Practice1_SrmAttendance {

    static class SrmStudent {

        String name;
        String regNo;
        int attendance;

        SrmStudent(String name, String regNo, int attendance) {
            this.name = name;
            this.regNo = regNo;
            this.attendance = attendance;
        }

        void addAttendanceUpdate(int newAttendance) {
            attendance = newAttendance;
        }

        boolean isEligible() {
            return attendance >= 75;
        }

        // classAverage is static because it calculates the average
        // for multiple students, not for one particular student.
        // isEligible is an instance method because it checks one student.
        static double classAverage(SrmStudent[] students) {

            int total = 0;

            for (SrmStudent student : students) {
                total += student.attendance;
            }

            return (double) total / students.length;
        }
    }

    public static void main(String[] args) {

        SrmStudent[] students = {
                new SrmStudent("Ravi", "RA231100301011", 82),
                new SrmStudent("Anitha", "RA231100301012", 68),
                new SrmStudent("Karthik", "RA231100301013", 91),
                new SrmStudent("Meera", "RA231100301014", 74),
                new SrmStudent("Suresh", "RA231100301015", 60)
        };

        for (SrmStudent student : students) {

            String status;

            if (student.isEligible()) {
                status = "Eligible";
            } else {
                status = "Detained";
            }

            System.out.println(
                    student.name + " - "
                            + student.attendance + "% - "
                            + status);
        }

        double average = SrmStudent.classAverage(students);

        System.out.println("Class average: " + average + "%");
    }
}