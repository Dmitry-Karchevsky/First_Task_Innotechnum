import java.util.LinkedList;
import java.util.List;

public class Table_Section {
    private List<Person> list = new LinkedList<>();
    private int table_section;
    private double average_salary = 0;

    public Table_Section(int table_section) {
        this.table_section = table_section;
    }

    public void addPerson(Person person){
        list.add(person);
    }

    public void removePerson(int id){
        list.remove(id);
    }

    public int getTable_section() {
        return table_section;
    }

    public double getAverage_salary() {
        return average_salary;
    }

    public int listPerson_Size(){
        return list.size();
    }

    public Person getPerson_from_List(int id) {
        return list.get(id);
    }

    public void calculate_section_salary() {
        average_salary = 0;
        for (Person person : list)
            average_salary += person.getSalary();
        average_salary /= list.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : list)
            stringBuilder.append(person.toString() + "\r\n");
        return stringBuilder.toString();
    }
}
