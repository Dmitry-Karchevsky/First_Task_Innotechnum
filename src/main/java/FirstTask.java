import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class FirstTask {

    /**
     * Данный метод возвращает сотрудника (Person) для добавления его в свой отдел
     * Метод позволяет вводить любое ФИО любой длинны,
     * но выдаст исключение при длине строки < 3 и если неверно указана зарплата сотрудника
     */
    private static Person createPerson(String[] info){
        if (info.length < 3)
            throw new IndexOutOfBoundsException();
        BigDecimal salary;
        try {
            salary = new BigDecimal(info[info.length - 1]);
        }
        catch (NumberFormatException e){
            throw e;
        }
        StringBuilder name = new StringBuilder();
        for (int i = info.length - 3; i >= 0; i--) {
            name.insert(0, info[i] + " ");
        }
        return new Person(name.toString().trim(), info[info.length - 2], salary);
    }

    /**
     * Данный метод возвращает заполненную Map, где key - название отдела, value - сам отдел
     * Релаизация Map - HashMap
     * При возникновении ошибок программа пропускает "ошибочную" строку и спускается дальше по файлу
     */
    private static Map<String, Department> FillInMap(String filepath){
        Map<String, Department> allDepartments = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filepath))){
            String line = fileReader.readLine();
            while (line != null) {
                try {
                    Person person = createPerson(line.split(" "));
                    if (!allDepartments.containsKey(person.getPersonDepartment()))
                        allDepartments.put(person.getPersonDepartment(), new Department(person.getPersonDepartment()));
                    allDepartments.get(person.getPersonDepartment()).addPerson(person);
                }
                catch (Exception e){}
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allDepartments;
    }

    private static void printMap(Map<String, Department> map){
        for (Department t : map.values()) {
            System.out.printf("Средняя зарплата в отделе %s: %s\nСостав отдела: \n",
                    t.getDepartmentName(), t.getAverageSalary().toString());
            System.out.println(t.toString());
        }
    }

    public static void main(String[] args) {
        Map<String, Department> allDepartments = FillInMap(args[0]);
        printMap(allDepartments);

        /**
         * Данный цикл скорее всего должен быть реализован рекурсией тк после его закрытия
         * появляются новые сотрудники для перевода в отделы тк средняя зарплата в отделах меняется
         *
         * Проверяемый отдел - i
         * Отдел в который может быть помещен сотрудник - j
         * Номер сотрудника в отделе - k
         *
         * Проверяем зарплату k-го сотрудника в i-ом отделе со средней зарплатой j-го отдела и со средней зарплатой i-го отдела
         * если меньше "j" или больше "i" идем дальше по сотрудникам и отделам
         * если больше переносим сотрудника в отдел
         */
        /*transfer: for (int i = 0; i < allDepartments.size(); i++) {
            for (int j = 0; j < allDepartments.size(); j++) {
                //Прооверка на тот же отдел
                if (i == j)
                    continue;
                for (int k = 0; k < allDepartments.get(i).listPerson_Size(); k++) {
                    if (allDepartments.get(i).getPersonFromList(k).getSalary() < allDepartments.get(i).getAverageSalary() &&
                            allDepartments.get(i).getPersonFromList(k).getSalary() > allDepartments.get(j).getAverageSalary()){
                        allDepartments.get(j).addPerson(allDepartments.get(i).getPersonFromList(k));
                        allDepartments.get(i).removePerson(k);
                        break transfer; //Выходим вообще из переводов тк теперь некоторые сотрудники могут быть доступны к переводу
                    }
                }
            }
        }*/

        System.out.println();
        for (Department t : allDepartments.values()) {
            System.out.println(t.getAverageSalary());
        }
    }
}
