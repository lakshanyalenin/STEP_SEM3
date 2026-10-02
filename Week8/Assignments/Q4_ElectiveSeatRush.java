import java.util.*;

interface CreditPolicy {
    int getCreditLimit();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }
}

class Student {
    String name;
    int currentCredits;
    CreditPolicy policy;

    Student(String name, int currentCredits, CreditPolicy policy) {
        this.name = name;
        this.currentCredits = currentCredits;
        this.policy = policy;
    }

    boolean canAddCredits(int credits) {
        return currentCredits + credits <= policy.getCreditLimit();
    }
}

class Elective {
    String name;
    int credits;
    int capacity;

    ArrayList<Student> enrolled = new ArrayList<>();
    Queue<Student> waitlist = new LinkedList<>();

    Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    boolean alreadyExists(Student student) {
        return enrolled.contains(student) || waitlist.contains(student);
    }

    void enroll(Student student) {

        // Check credit limit BEFORE seat availability
        if (!student.canAddCredits(credits)) {
            System.out.println(student.name +
                    " enrollment failed: credit limit exceeded");
            return;
        }

        // Prevent duplicate enrollment/waitlisting
        if (alreadyExists(student)) {
            System.out.println(student.name +
                    " already enrolled/waitlisted");
            return;
        }

        if (enrolled.size() < capacity) {
            enrolled.add(student);
            student.currentCredits += credits;

            System.out.println(student.name +
                    " enrolled in " + name);
        } else {
            waitlist.add(student);

            System.out.println(student.name +
                    " added to waitlist");
        }
    }

    void drop(Student student) {

        if (!enrolled.remove(student)) {
            System.out.println(student.name +
                    " is not enrolled");
            return;
        }

        student.currentCredits -= credits;

        System.out.println(student.name +
                " dropped " + name);

        promoteNext();
    }

    void promoteNext() {

        if (enrolled.size() >= capacity)
            return;

        Iterator<Student> iterator = waitlist.iterator();

        while (iterator.hasNext()) {
            Student student = iterator.next();

            // Credit limit checked again during promotion
            if (student.canAddCredits(credits)) {

                iterator.remove();

                enrolled.add(student);
                student.currentCredits += credits;

                System.out.println(student.name +
                        " promoted from waitlist");

                return;
            }
        }

        System.out.println("No eligible student for promotion");
    }

    void showStatus() {
        System.out.println("\nEnrolled students:");

        for (Student student : enrolled) {
            System.out.println(student.name +
                    " - " + student.currentCredits + " credits");
        }

        System.out.println("Waitlist:");

        for (Student student : waitlist) {
            System.out.println(student.name);
        }
    }
}

class EnrollmentService {

    void enroll(Elective elective, Student student) {
        elective.enroll(student);
    }

    void drop(Elective elective, Student student) {
        elective.drop(student);
    }
}

public class Q4_ElectiveSeatRush {

    public static void main(String[] args) {

        Elective cloud = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", 20, new RegularPolicy());

        Student ravi = new Student("Ravi", 22, new HonorsPolicy());

        Student neha = new Student("Neha", 12, new ExchangePolicy());

        Student kiran = new Student("Kiran", 22, new RegularPolicy());

        EnrollmentService service = new EnrollmentService();

        service.enroll(cloud, asha);
        service.enroll(cloud, ravi);

        service.enroll(cloud, neha);

        service.enroll(cloud, kiran);

        cloud.showStatus();

        System.out.println("\nAsha drops the elective:");

        service.drop(cloud, asha);

        cloud.showStatus();
    }
}