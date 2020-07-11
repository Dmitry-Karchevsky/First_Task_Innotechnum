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

    public Person(Person value){
        this.id = count;
        this.name = value.getName();
        this.department = value.getDepartment();
        this.salary = value.getSalary();
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

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return name + " " + department + " " + salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(salary, person.salary) &&
                Objects.equals(name, person.name) &&
                Objects.equals(department, person.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary, name, department);
    }
}
