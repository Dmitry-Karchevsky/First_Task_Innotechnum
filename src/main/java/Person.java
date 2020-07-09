import java.math.BigDecimal;

public class Person {
    private static int count = 0;

    private int id;
    private BigDecimal salary;
    private String name;
    private String department;

    public Person(String name, String department, BigDecimal salary) {
        count++;
        this.id = count;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPersonDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " " + department + " " + salary;
    }
}
