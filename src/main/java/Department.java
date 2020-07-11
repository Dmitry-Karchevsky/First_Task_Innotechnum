import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Department {
    private List<Person> list = new LinkedList<>();
    private String departmentName;
    private BigDecimal averageSalary;

    public Department(String departmentSection) {
        this.departmentName = departmentSection;
    }

    public Department(Department value) {
        //this.list = new LinkedList<>(value.getPersonsList());// ссылается на одни и те же объекты
        //this.list = List.copyOf(value.getPersonsList());
        list = new LinkedList<>();
        for (Person p : value.getPersonsList())
            list.add(p);
        this.departmentName = value.getDepartmentName();
        this.averageSalary = value.getAverageSalary();
    }

    public void addPerson(Person person){
        person.setDepartment(departmentName);
        list.add(person);
        //list.get(list.size() - 1).setDepartment(departmentName);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(averageSalary, that.averageSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, departmentName, averageSalary);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
