package firsttask;

import java.math.BigDecimal;
import java.util.Objects;

public class Person {
    private static int count = 0;

    private int id;
    private BigDecimal salary;
    private String name;

    public Person(String name, BigDecimal salary) {
        count++;
        this.id = count;
        this.name = name;
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Имя: " + name + ", Зарплата: " + salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(salary, person.salary) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salary, name);
    }
}
