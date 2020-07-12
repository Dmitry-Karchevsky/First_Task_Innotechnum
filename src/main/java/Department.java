import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Department implements Cloneable{
    private List<Person> list = new LinkedList<>();
    private String departmentName;
    private BigDecimal averageSalary;

    public Department(String departmentSection) {
        this.departmentName = departmentSection;
    }

    public void addPerson(Person person){
        list.add(new Person(person.getId(), person.getName(), departmentName, person.getSalary()));
    }

    public void removePerson(int id){
        list.remove(id);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public BigDecimal getAverageSalary() {
        averageSalary = new BigDecimal(0);
        for (Person person : list)
            averageSalary = averageSalary.add(person.getSalary());
        averageSalary = averageSalary.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        return averageSalary;
    }

    public List<Person> getPersonsList() {
        return list;
    }

    public int listPersonSize(){
        return list.size();
    }

    public Person getPersonFromList(int id) {
        return list.get(id);
    }

    @Override
    public Department clone() throws CloneNotSupportedException {
        Department newDepartment = (Department) super.clone();
        newDepartment.list = new LinkedList<>();
        newDepartment.list.addAll(this.getPersonsList());
        return newDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(getAverageSalary(), that.getAverageSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, departmentName, getAverageSalary());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
