import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class FirstTask {

    private static Map<String, Department> allDepartments;
    private static Set<Map<String, Department>> setDepartmentsVariants = new HashSet<>();

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
     * При ненахождении файла программа выводит соответствуещее сообщение и завершается
     */
    private static Map<String, Department> readFileInMap(String fileName){
        Map<String, Department> allDepartments = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
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
            System.out.println("Данный файл недоступен");
            System.exit(0);
        }
        return allDepartments;
    }

    /**
     * Записывает в файл все варианты распределения сотрудников из setDepartmentsVariants
     */
    private static void writeMapInFile(String fileName){
        try(FileWriter writer = new FileWriter(fileName, false))
        {
            int var = 1;
            writer.write("\tВАРИАНТЫ ПЕРЕВОДОВ СОТРУДНИКОВ:\n");
            for (Map<String, Department> map : setDepartmentsVariants) {
                writer.write("Вариант " + var + ":\n");
                writer.write(getStringMap(map));
                var++;
            }
            writer.flush();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Возвращает собранную строку с информации об одном варианте распределения отделов
     */
    private static String getStringMap(Map<String, Department> map){
        StringBuilder mapStr = new StringBuilder();
        for (Department t : map.values()) {
            mapStr.append("Средняя зарплата в отделе " + t.getDepartmentName() +
                            ": " + t.getAverageSalary().toString() +
                            "\nСостав отдела: \n");
            mapStr.append(t.toString() + "\n");
        }
        return mapStr.toString();
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
    private static void transferPersons(Map<String, Department> map){
        boolean end = true;
        while (end) {
            Map<String, Department> anotherVariantMap = cloneMap(map);
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
                                departmentPairOut.getValue().addPerson(departmentPairIn.getValue().getPersonFromList(departmentPairIn.getValue().listPersonSize() - 1));
                                departmentPairIn.getValue().removePerson(departmentPairIn.getValue().listPersonSize() - 1);
                                continue;
                            }
                            setDepartmentsVariants.add(anotherVariantMap);
                            Map<String, Department> anotherVariantMap2 = cloneMap(anotherVariantMap);
                            transferPersons(anotherVariantMap2);
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
        allDepartments = readFileInMap(args[0]);

        transferPersons(allDepartments);

        writeMapInFile(args[1]);
    }
}
