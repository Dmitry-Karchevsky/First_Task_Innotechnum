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

        // *Потом обернуть в try catch
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
        fileReader.close();

        for (Table_Section t : tables) {
            t.calculate_section_salary();
            System.out.println(t.getAverage_salary());
        }

        /**
         * Данный цикл скорее всего должен быть реализован рекурсией тк после его закрытия
         * появляются новые сотрудники для перевода в отделы тк средняя зарплата в отделах меняется
         *
         * Проверяемый отдел - i
         * Отдел в который может быть помещен сотрудник - j
         * Номер сотрудника в отделе - k
         */
        transfer: for (int i = 0; i < tables.size(); i++) {
            for (int j = 0; j < tables.size(); j++) {
                //Прооверка на тот же отдел
                if (i == j)
                    continue;
                /**
                 * Проверяем зарплату k-го сотрудника в i-ом отделе со средней зарплатой j-го отдела и со средней зарплатой i-го отдела
                 * если меньше "j" или больше "i" идем дальше по сотрудникам и отделам
                 * если больше переносим сотрудника в отдел
                 */
                for (int k = 0; k < tables.get(i).listPerson_Size(); k++) {
                    if (tables.get(i).getPerson_from_List(k).getSalary() < tables.get(i).getAverage_salary() &&
                            tables.get(i).getPerson_from_List(k).getSalary() > tables.get(j).getAverage_salary()){
                        tables.get(j).addPerson(tables.get(i).getPerson_from_List(k));
                        tables.get(i).getPerson_from_List(k).setSection(tables.get(j).getTable_section());
                        tables.get(i).removePerson(k);
                        break transfer; //Выходим вообще из переводов тк теперь некоторые сотрудники могут быть доступны к переводу
                    }
                }
            }
        }

        System.out.println();
        for (Table_Section t : tables) {
            t.calculate_section_salary();
            System.out.println(t.getAverage_salary());
        }
    }
}
