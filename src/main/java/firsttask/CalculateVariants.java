package firsttask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculateVariants {

    private static final List<String> allTransferVariants = new ArrayList<>();

    /**
     * Вывод средней зарплаты в списке
     */
    public static BigDecimal getAverageSalary(List<Person> list) {
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
        StringBuilder variant = new StringBuilder("Сотрудники: \n");
        for (Person person : list)
            variant.append(person).append("\n");
        variant.append("Из отдела ").
                append(out.getDepartmentName()).
                append(" могут быть перемещены в отдел ").
                append(in.getDepartmentName()).
                append("\nСредняя зарплата до перемещения\nВ отделе ").
                append(out.getDepartmentName()).
                append(": ").
                append(getAverageSalary(out.getPersonsList())).
                append("\nВ отделе ").
                append(in.getDepartmentName()).
                append(": ").
                append(getAverageSalary(in.getPersonsList())).
                append("\nСредняя зарплата после перемещения\n");

        List<Person> tempListOut = out.getPersonsList();
        List<Person> tempListIn = in.getPersonsList();

        tempListIn.addAll(list);
        tempListOut.removeAll(list);
        variant.append("В отделе ").
                append(out.getDepartmentName()).
                append(": ").
                append(getAverageSalary(tempListOut)).
                append("\nВ отделе ").
                append(in.getDepartmentName()).
                append(": ").
                append(getAverageSalary(tempListIn)).
                append("\n");
        allTransferVariants.add(variant.toString());
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
            List<List<Person>> allVariantsOfLists = recursionSearch(departmentPairOut.getPersonsList(), 0, new ArrayList<>(), getAverageSalary(departmentPairOut.getPersonsList()));
            for (Department departmentPairIn : allDepartments) {
                if (departmentPairOut.getDepartmentName().equals(departmentPairIn.getDepartmentName()))
                    continue;
                for (List<Person> list : allVariantsOfLists){
                    if (getAverageSalary(list).compareTo(getAverageSalary(departmentPairIn.getPersonsList())) > 0)
                        addVariant(departmentPairOut, departmentPairIn, list);
                }
            }
        }
        return allTransferVariants;
    }

    // finalList - это все сотрудники определенного отдела (неизменяемый список сотрудников)
    // allVariantsOfLists - это список всех наборов сотрудников определенного отдела
    private static List<List<Person>> recursionSearch(List<Person> finalList, int start, List<Person> list, BigDecimal averageSalaryOut){
        List<List<Person>> allVariantsOfLists = new ArrayList<>();
        for (int i = start; i < finalList.size(); i++) {
            List<Person> nowList = new ArrayList<>(list);
            nowList.add(finalList.get(i));
            if (getAverageSalary(nowList).compareTo(averageSalaryOut) < 0)
                allVariantsOfLists.add(nowList);
            allVariantsOfLists.addAll(recursionSearch(finalList, i + 1, nowList, averageSalaryOut));
        }
        return allVariantsOfLists;
    }
}
