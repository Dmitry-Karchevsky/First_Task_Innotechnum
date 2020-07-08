import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FirstTask {
    public static void main(String[] args) throws IOException {
        List<Table_Section> tables = new LinkedList<>();
        Set<Integer> nums_of_sections = new HashSet<>();

        BufferedReader fileReader = new BufferedReader(new FileReader("src\\main\\resources\\Input_file.txt"));
        String line = fileReader.readLine();
        while (line != null) {
            //Считываем данные о работнике из файла
            String[] arr_of_str = line.split(" ");
            //Создаем работника и смотрим есть ли отдел, в котором он работает, если нет, то создаем этот отдел
            Person person = new Person(Integer.valueOf(arr_of_str[0]), Integer.valueOf(arr_of_str[1]), Integer.valueOf(arr_of_str[2]));
            if (!nums_of_sections.contains(person.getSection())) {
                tables.add(new Table_Section(person.getSection()));
                nums_of_sections.add(person.getSection());
            }
            //Смотрим к какому отделу принадлежит наш работник
            for (int i = 0; i < tables.size(); i++) {
                if (tables.get(i).getTable_section() == person.getSection()){
                    tables.get(i).addPerson(person);
                    break;
                }
            }
            line = fileReader.readLine();
        }

        //Проверка на правильный ввод данных
        for (Table_Section t : tables)
            System.out.println(t);
        /*1 1 30000
        2 1 12000
        4 1 38000


        3 2 43000
        7 2 10000


        5 3 14000
        6 3 44000*/
    }
}
