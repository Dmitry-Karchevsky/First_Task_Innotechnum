import java.util.LinkedList;
import java.util.List;

public class Table_Section {
    private List<Person> list = new LinkedList<>();
    private int table_section;
    private int average_salary;

    public Table_Section(int table_section) {
        this.table_section = table_section;
    }

    public void addPerson(Person person){
        list.add(person);
    }

    public int getTable_section() {
        return table_section;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
