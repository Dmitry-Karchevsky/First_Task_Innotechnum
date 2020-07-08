public class Person {
    private int id;
    private int section;
    private int salary;

    public Person(int id, int section, int salary) {
        this.id = id;
        this.section = section;
        this.salary = salary;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public int getSection() {
        return section;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return id + " " + section + " " + salary;
    }
}
