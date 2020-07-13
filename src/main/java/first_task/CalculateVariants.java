package first_task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculateVariants {

    private static List<String> allTransferVariants = new LinkedList<>();

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
    private static void addVariant(Department out, Department in, int idPersonInList){
        StringBuilder variant = new StringBuilder();
        variant.append("Сотрудник " + out.getPersonFromList(idPersonInList) + " из отдела "
                + out.getDepartmentName() + " может быть перемещен в отдел " + in.getDepartmentName() +
                "\nСредняя зарплата до перемещения\nВ отделе " + out.getDepartmentName() + ": " + getAverageSalary(out.getPersonsList()) +
                "\nВ отделе " + in.getDepartmentName() + ": " + getAverageSalary(in.getPersonsList()) +
                "\nСредняя зарплата после перемещения\n");

        List<Person> tempListOut = (LinkedList<Person>)out.getPersonsList().clone();
        List<Person> tempListIn = (LinkedList<Person>)in.getPersonsList().clone();
        try {
            tempListIn.add(tempListOut.get(idPersonInList).clone());
        } catch (CloneNotSupportedException e) {
            System.out.printf("Класс %s не имеет реализации интерфейса Cloneable", out.getClass());
        }
        tempListOut.remove(idPersonInList);
        variant.append("В отделе " + out.getDepartmentName() + ": " + getAverageSalary(tempListOut) +
                "\nВ отделе " + in.getDepartmentName() + ": " + getAverageSalary(tempListIn));
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
    public static List<String> findVariants(Map<String, Department> allDepartments){
        for (Map.Entry<String, Department> departmentPairOut : allDepartments.entrySet()) {
            for (Map.Entry<String, Department> departmentPairIn : allDepartments.entrySet()) {
                if (departmentPairOut.getKey().equals(departmentPairIn.getKey()))
                    continue;
                for (int k = 0; k < departmentPairOut.getValue().getPersonsList().size(); k++) {
                    if (departmentPairOut.getValue().getPersonFromList(k).getSalary().
                            compareTo(getAverageSalary(departmentPairOut.getValue().getPersonsList())) < 0 &&
                            departmentPairOut.getValue().getPersonFromList(k).getSalary().
                                    compareTo(getAverageSalary(departmentPairIn.getValue().getPersonsList())) > 0) {

                        addVariant(departmentPairOut.getValue(), departmentPairIn.getValue(), k);
                    }
                }
            }
        }
        return allTransferVariants;
    }
}
