package first_task;

import java.util.Map;

public class FirstTask {

    private static Map<String, Department> allDepartments;

    public static void main(String[] args) {
        try {
            allDepartments = ReadFile.readFileInMap(args[0]);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Имя входного файла не введено");
        }

        if (allDepartments != null) {
            try {
                WriteFile.writeVariantsInFile(args[1], CalculateVariants.findVariants(allDepartments));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Имя файла для вывода не введено");
            }
        }
    }
}
