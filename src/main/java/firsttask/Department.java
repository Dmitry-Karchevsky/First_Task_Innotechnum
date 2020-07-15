package firsttask;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private List<Person> list = new ArrayList<>();
    private String departmentName;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public List<Person> getPersonsList() {
        return new ArrayList<>(list);
    }

    public void addPerson(Person person) {
        list.add(person);
    }
}
