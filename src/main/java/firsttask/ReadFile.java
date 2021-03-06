package firsttask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ReadFile {
    /**
     * Данный метод возвращает сотрудника (FirstTask.Person) для добавления его в свой отдел
     * Метод позволяет вводить любое ФИО любой длинны,
     * но выдаст исключение если неверно указана зарплата сотрудника
     */
    private static Person createPerson(String[] info){
        BigDecimal salary;
        try {
            salary = new BigDecimal(info[info.length - 1].trim());
            if (salary.compareTo(BigDecimal.ZERO) < 0 || salary.scale() > 2)
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
    public static List<Department> readFileInList(String fileName) {
        int numStr = 0;
        String[] info = new String[0];
        Map<String, Department> allMaps = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
            String line = fileReader.readLine();
            while (line != null) {
                numStr++;
                try {
                    info = line.split(";");
                    if (info.length != 3)
                        throw new IndexOutOfBoundsException();
                    Person person = createPerson(info);
                    String departmentName = info[1].trim();
                    if (!allMaps.containsKey(departmentName))
                        allMaps.put(departmentName, new Department(departmentName));
                    allMaps.get(departmentName).addPerson(person);
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

        List<Department> allLists = new ArrayList<>(allMaps.values());
        printDepartmentList(allLists);
        return allLists;
    }

    private static void printDepartmentList(List<Department> listDep){
        System.out.println("\n\tИНФОРМАЦИЯ О СЧИТАННЫХ ОТДЕЛАХ");
        StringBuilder listStr = new StringBuilder();
        for (Department dep : listDep) {
            listStr.append("\nСредняя зарплата в отделе ").
                    append(dep.getDepartmentName()).
                    append(": ").
                    append(CalculateVariants.getAverageSalary(dep.getPersonsList())).
                    append("\nСостав отдела: ").
                    append(dep.getDepartmentName()).
                    append(dep.toString());
        }
        System.out.println(listStr.toString());
    }
}
