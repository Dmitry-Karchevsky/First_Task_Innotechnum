package firsttask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculateVariants {

    private static final List<String> allTransferVariants = new ArrayList<>();
    private static List<List<Person>> allVariantsOfLists;

    /**
     * Вывод средней зарплаты в списке
     */
    private static BigDecimal getAverageSalary(List<Person> list) {
        BigDecimal averageSalary = new BigDecimal(0);
        for (Person person : list)
            averageSalary = averageSalary.add(person.getSalary());
        averageSalary = averageSalary.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        return averageSalary;
    }

    /**
     * Добавляем вариант распределения сотрудников в List
     */
    private static void addVariant(Department out, Department in, List<Person> list){
        String variant = "Сотрудники: \n";
        for (Person person : list)
            variant += (person + "\n");
        variant += "Из отдела " + out.getDepartmentName() + " могут быть перемещены в отдел " + in.getDepartmentName() +
                "\nСредняя зарплата до перемещения\nВ отделе " + out.getDepartmentName() + ": " + getAverageSalary(out.getPersonsList()) +
                "\nВ отделе " + in.getDepartmentName() + ": " + getAverageSalary(in.getPersonsList()) +
                "\nСредняя зарплата после перемещения\n";

        List<Person> tempListOut = out.getPersonsList();
        List<Person> tempListIn = in.getPersonsList();

        tempListIn.addAll(list);
        tempListOut.removeAll(list);
        variant += "В отделе " + out.getDepartmentName() + ": " + getAverageSalary(tempListOut) +
                "\nВ отделе " + in.getDepartmentName() + ": " + getAverageSalary(tempListIn) + "\n";
        allTransferVariants.add(variant);
    }

    /**
     * Проверяемый отдел - departmentPairOut
     * Отдел в который может быть помещен сотрудник - departmentPairIn
     * Номер сотрудника в отделе - k
     *
     * Проверяем зарплату k-го сотрудника в отделе (departmentPairOut) со средней зарплатой отдела (departmentPairIn)
     * и со средней зарплатой отдела (departmentPairOut)
     * если меньше "departmentPairIn" или больше "departmentPairOut" идем дальше по сотрудникам и отделам
     * если больше - вызываем addVariant
     */
    public static List<String> findVariants(List<Department> allDepartments){
        for (Department departmentPairOut : allDepartments) {
            for (Department departmentPairIn : allDepartments) {
                if (departmentPairOut.getDepartmentName().equals(departmentPairIn.getDepartmentName()))
                    continue;

                allVariantsOfLists = new ArrayList<>();
                recursionSearch(departmentPairOut.getPersonsList(), 0, new ArrayList<>());

                for (List<Person> list : allVariantsOfLists){
                    if (getAverageSalary(list).compareTo(getAverageSalary(departmentPairOut.getPersonsList())) < 0 &&
                            getAverageSalary(list).compareTo(getAverageSalary(departmentPairIn.getPersonsList())) > 0) {

                        addVariant(departmentPairOut, departmentPairIn, list);
                    }
                }
            }
        }
        return allTransferVariants;
    }

    // finalList - это все сотрудники определенного отдела (неизменяемый список сотрудников)
    // allVariantsOfLists - это список всех наборов сотрудников определенного отдела
    private static void recursionSearch(List<Person> finalList, int start, List<Person> list){
        for (int i = start; i < finalList.size(); i++) {
            List<Person> nowList = new ArrayList<>(list);
            nowList.add(finalList.get(i));
            allVariantsOfLists.add(nowList);
            recursionSearch(finalList, i + 1, nowList);
        }
    }
}
