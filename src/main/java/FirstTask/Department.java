package FirstTask;

import java.util.*;

public class Department {
    private LinkedList<Person> list = new LinkedList<>();
    private String departmentName;

    public Department(String departmentSection) {
        this.departmentName = departmentSection;
    }

    public void addPerson(Person person){
        list.add(person);
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public LinkedList<Person> getPersonsList() {
        return list;
    }

    public Person getPersonFromList(int id) {
        return list.get(id);
    }
}
