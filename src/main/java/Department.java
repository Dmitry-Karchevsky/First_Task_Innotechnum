import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Department {
    private List<Person> list = new LinkedList<>();
    private String departmentName;
    private BigDecimal averageSalary;

    public Department(String departmentSection) {
        this.departmentName = departmentSection;
    }

    public void addPerson(Person person){
        person.setDepartment(departmentName);
        list.add(person);
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
        averageSalary = averageSalary.divide(new BigDecimal(list.size()));
        return averageSalary;
    }

    public int listPerson_Size(){
        return list.size();
    }

    public Person getPersonFromList(int id) {
        return list.get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
