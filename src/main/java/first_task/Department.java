package first_task;

import java.util.LinkedList;

public class Department {
    private LinkedList<Person> list;
    private String departmentName;

    public Department(String departmentName, LinkedList<Person> list) {
        this.list = list;
        this.departmentName = departmentName;
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
