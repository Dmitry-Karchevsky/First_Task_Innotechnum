import java.math.BigDecimal;
import java.util.Objects;

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

    public Person(int id, String name, String department, BigDecimal salary) {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.department = department;
    }

    public String getPersonDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(salary, person.salary) &&
                Objects.equals(name, person.name) &&
                Objects.equals(department, person.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, name, department);
    }

    @Override
    public String toString() {
        return name + " " + department + " " + salary;
    }
}
