public class Assignment2_EmployeeInheritance {

    static class Employee {

        private String empId;
        private String empName;
        private double salary;

        Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {

        private double teamBonus;

        ManagerEmployee(String empId, String empName,
                double salary, double teamBonus) {

            super(empId, empName, salary);
            this.teamBonus = teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class InternEmployee extends Employee {

        private double stipendCap;

        InternEmployee(String empId, String empName,
                double salary, double stipendCap) {

            super(empId, empName, salary);
            this.stipendCap = stipendCap;
        }

        double effectiveSalary() {
            if (getSalary() < stipendCap) {
                return getSalary();
            }

            return stipendCap;
        }
    }

    public static void main(String[] args) {

        Employee plainEmployee = new Employee("E101", "Arun", 40000);

        Employee manager = new ManagerEmployee("E102", "Priya", 70000, 8000);

        Employee intern = new InternEmployee("E103", "Rahul", 12000, 10000);

        if (plainEmployee instanceof ManagerEmployee) {

            ManagerEmployee m = (ManagerEmployee) plainEmployee;
            System.out.println("Manager effective pay: Rs "
                    + m.effectiveSalary());

        } else if (plainEmployee instanceof InternEmployee) {

            InternEmployee i = (InternEmployee) plainEmployee;
            System.out.println("Intern effective pay: Rs "
                    + i.effectiveSalary());

        } else {

            System.out.println("Plain employee pay: Rs "
                    + plainEmployee.getSalary());
        }

        if (manager instanceof ManagerEmployee) {

            ManagerEmployee m = (ManagerEmployee) manager;
            System.out.println("Manager effective pay: Rs "
                    + m.effectiveSalary());
        }

        if (intern instanceof InternEmployee) {

            InternEmployee i = (InternEmployee) intern;
            System.out.println("Intern effective pay: Rs "
                    + i.effectiveSalary());
        }
    }
}