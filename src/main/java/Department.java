import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Department{
    private List<Person> list = new LinkedList<>();
    private String departmentName;
    private BigDecimal averageSalary;

    public Department(String departmentSection) {
        this.departmentName = departmentSection;
    }

    /*public Department(List<Person> list, String departmentName, BigDecimal averageSalary) {
        this.list = list;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }*/

    public Department(Department value) {
        //this.list = new LinkedList<>(value.getPersonsList());// ссылается на одни и те же объекты
        //this.list = List.copyOf(value.getPersonsList());
        list = new LinkedList<>();
        list.addAll(value.getPersonsList());
        this.departmentName = value.getDepartmentName();
        this.averageSalary = value.getAverageSalary();
    }

    public void addPerson(Person person){
        //person.setDepartment(departmentName);
        //list.add(person);
        list.add(new Person(person.getName(), departmentName, person.getSalary()));
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

    /*@Override
    protected Object clone() throws CloneNotSupportedException {
        List<Person> newList = new LinkedList<>();
        newList.addAll(this.getPersonsList());
        return new Department(newList, this.getDepartmentName(), this.getAverageSalary());
    }*/

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
        //return list.hashCode() ^ departmentName.hashCode() ^ averageSalary.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
