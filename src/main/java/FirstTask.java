import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class FirstTask {

    private static Map<String, Department> allDepartments;
    private static Set<Map<String, Department>> setDepartmentsVariants;

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
                catch (Exception e){
                    System.out.println("Один из сотрудников записан неверно");
                }
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allDepartments;
    }

    /**
     * Читаемый вывод в консоль
     */
    private static void printMap(Map<String, Department> map){
        for (Department t : map.values()) {
            System.out.printf("Средняя зарплата в отделе %s: %s\nСостав отдела: \n",
                    t.getDepartmentName(), t.getAverageSalary().toString());
            System.out.println(t.toString());
        }
    }

    /**
     * Метод клонирования Map
     * Необходим для неизменности allDepartments
     */
    private static Map<String, Department> cloneMap( Map<String, Department> srcMap) {
        Map<String, Department> destMap = new HashMap<>();
        for(Map.Entry<String, Department> entry : srcMap.entrySet()) {
            destMap.put(entry.getKey(), new Department(entry.getValue()));
        }
        return destMap;
    }

    /**
     * Проверяемый отдел - departmentPairOut
     * Отдел в который может быть помещен сотрудник - departmentPairIn
     * Номер сотрудника в отделе - k
     *
     * Проверяем зарплату k-го сотрудника в отделе (departmentPairOut) со средней зарплатой отдела (departmentPairIn)
     * и со средней зарплатой отдела (departmentPairOut)
     * если меньше "departmentPairIn" или больше "departmentPairOut" идем дальше по сотрудникам и отделам
     * если больше - переносим сотрудника в отдел и проверяем данный отдел на наличие такого же в setDepartmentsVariants
     */
    private static void transferPerson(){
        boolean end = true;
        while (end) {
            Map<String, Department> anotherVariantMap = cloneMap(allDepartments);
            transfer:
            for (Map.Entry<String, Department> departmentPairOut : anotherVariantMap.entrySet()) {
                for (Map.Entry<String, Department> departmentPairIn : anotherVariantMap.entrySet()) {
                    if (departmentPairOut.getKey().equals(departmentPairIn.getKey()))
                        continue;
                    for (int k = 0; k < departmentPairOut.getValue().listPersonSize(); k++) {
                        if (departmentPairOut.getValue().getPersonFromList(k).getSalary().
                                compareTo(departmentPairOut.getValue().getAverageSalary()) < 0 &&
                                departmentPairOut.getValue().getPersonFromList(k).getSalary().
                                        compareTo(departmentPairIn.getValue().getAverageSalary()) > 0) {
                            departmentPairIn.getValue().addPerson(departmentPairOut.getValue().getPersonFromList(k));
                            departmentPairOut.getValue().removePerson(k);
                            if (setDepartmentsVariants.contains(anotherVariantMap)) {
                                //Если такой отдел есть - меняем местами обратно
                                departmentPairOut.getValue().addPerson(departmentPairIn.getValue().getPersonFromList(departmentPairIn.getValue().listPersonSize() - 1));
                                departmentPairIn.getValue().removePerson(departmentPairIn.getValue().listPersonSize() - 1);
                                continue;
                            }
                            setDepartmentsVariants.add(anotherVariantMap);
                            end = true;
                            break transfer;
                        }
                        end = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        allDepartments = FillInMap(args[0]);
        printMap(allDepartments);
        setDepartmentsVariants = new HashSet<>();
        transferPerson();
        System.out.println("\tВАРИАНТЫ ПЕРЕВОДОВ СОТРУДНИКОВ:");
        int var = 1;
        for (Map<String, Department> map : setDepartmentsVariants) {
            System.out.printf("Вариант %d:\n", var);
            printMap(map);
            var++;
        }
    }
}
