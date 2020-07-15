package first_task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReadFile {
    /**
     * Данный метод возвращает сотрудника (FirstTask.Person) для добавления его в свой отдел
     * Метод позволяет вводить любое ФИО любой длинны,
     * но выдаст исключение если неверно указана зарплата сотрудника
     */
    private static Person createPerson(String[] info){
        BigDecimal salary;
        try {
            salary = new BigDecimal(info[info.length - 1].trim()).setScale(2, RoundingMode.HALF_UP);
            if (salary.compareTo(BigDecimal.ZERO) < 0)
                throw new NumberFormatException();
        }
        catch (NumberFormatException e){
            throw e;
        }
        return new Person(info[0].trim(), salary);
    }

    /**
     * Данный метод возвращает заполненную Map, где key - название отдела, value - сам отдел
     * Релаизация Map - HashMap
     * При возникновении ошибок программа пропускает "ошибочную" строку и спускается дальше по файлу
     * При ненахождении файла программа выводит соответствуещее сообщение
     */
    public static List<Department> readFileInMap(String fileName) {
        int numStr = 0;
        String[] info = new String[0];
        Map<String, List<Person>> allMaps = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
            String line = fileReader.readLine();
            while (line != null) {
                numStr++;
                try {
                    info = line.split(";");
                    if (info.length != 3)
                        throw new IndexOutOfBoundsException();
                    Person person = createPerson(info);
                    String departmentName = info[info.length - 2].trim();
                    if (!allMaps.containsKey(departmentName))
                        allMaps.put(departmentName, new LinkedList<Person>());
                    allMaps.get(departmentName).add(person);
                }
                catch (IndexOutOfBoundsException e){
                    System.out.printf("В строке №%d с данными \"%s\" сотрудник записан неверно: ", numStr, line);
                    if (info.length < 3)
                        System.out.println("(не хватает данных)");
                    else
                        System.out.println("(много данных)");
                }
                catch (NumberFormatException e){
                    System.out.printf("В строке №%d с данными \"%s\" сотрудник записан неверно: (неверно указана зарплата)\n", numStr, line);
                }
                line = fileReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Файл для считывания данных: %s не найден\n", fileName);
            return null;
        }
        catch (IOException e){
            System.out.printf("Ошибка при чтении данных из файла: %s\n", fileName);
            return null;
        }
        List<Department> allDepartments = new LinkedList<>();
        for (Map.Entry pair : allMaps.entrySet()){
            allDepartments.add(new Department((String)pair.getKey(), (LinkedList<Person>) pair.getValue()));
        }
        return allDepartments;
    }
}
