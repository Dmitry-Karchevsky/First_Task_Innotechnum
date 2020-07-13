package FirstTask;

import java.math.BigDecimal;

public class Person implements Cloneable{
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
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    @Override
    public String toString() {
        return "Имя: " + name + ", Зарплата: " + salary;
    }
}
