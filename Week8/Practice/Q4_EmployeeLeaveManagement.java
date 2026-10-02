import java.time.LocalDate;

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract boolean isLeaveAllowed(int days);
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String name) {
        super(name);
    }

    public boolean isLeaveAllowed(int days) {
        return days <= 20;
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String name) {
        super(name);
    }

    public boolean isLeaveAllowed(int days) {
        return days <= 10;
    }
}

class LeaveRequest {
    Employee employee;
    LocalDate startDate;
    LocalDate endDate;
    String status = "Pending";

    public LeaveRequest(Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void approve() {
        if (status.equals("Pending")) {
            status = "Approved";
            System.out.println("Leave request for "
                    + employee.name + " approved. Status: " + status);
        }
    }

    public void reject() {
        if (status.equals("Pending")) {
            status = "Rejected";
            System.out.println("Leave request for "
                    + employee.name + " rejected. Status: " + status);
        }
    }

    public void setPending() {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change status: "
                    + status + " request cannot revert to Pending.");
        }
    }
}

class LeaveManager {

    public void submitRequest(LeaveRequest request) {
        System.out.println("Leave request submitted by "
                + request.employee.name
                + " for "
                + request.startDate
                + " to "
                + request.endDate
                + ". Status: "
                + request.status);
    }

    public void reviewAndApprove(LeaveRequest request) {
        request.approve();
    }
}

public class Q4_EmployeeLeaveManagement {

    public static void main(String[] args) {

        Employee john = new FullTimeEmployee("John Doe");

        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest johnRequest = new LeaveRequest(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        LeaveRequest janeRequest = new LeaveRequest(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        LeaveManager manager = new LeaveManager();

        manager.submitRequest(johnRequest);

        manager.reviewAndApprove(johnRequest);

        manager.submitRequest(janeRequest);

        johnRequest.setPending();
    }
}