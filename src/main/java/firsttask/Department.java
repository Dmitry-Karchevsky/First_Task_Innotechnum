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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (Person person : list)
            stringBuilder.append(person.toString()).append("\r\n");
        return stringBuilder.toString();
    }
}
