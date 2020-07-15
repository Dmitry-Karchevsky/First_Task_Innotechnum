package firsttask;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {

    /**
     * Записывает в файл все варианты распределения сотрудников
     */
    public static void writeVariantsInFile(String fileName, List<String> list) {
        try(FileWriter writer = new FileWriter(fileName, false))
        {
            int var = 1;
            writer.write("\tВАРИАНТЫ ПЕРЕВОДОВ СОТРУДНИКОВ:\n");
            for (String variant : list) {
                writer.write("\nВариант " + var + ":\n" + variant);
                var++;
            }
            writer.flush();
        } catch (IOException e) {
            System.out.printf("Недопустимое имя файла для вывода: %s\n", fileName);
        }
    }
}
