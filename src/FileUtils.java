import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class FileUtils {

    /**
     * Создать файл с заданным именем
     *
     * @param fileName имя файла, который необходимо создать
     * @return true если файл был успешно создан или уже существует; false если файл не мог быть создан
     * @throws NullPointerException если {@code fileName} имеет значение {@code null}
     */
    public static boolean createFile(String fileName) {
        Objects.requireNonNull(fileName, "Имя файл не должно равняться null");

        File file = new File(fileName);
        if (file.exists()) {
            return true; // Файл уже был создан
        }

        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cоздать файлы с заданными именами
     *
     * @param fileNames имена файлов, которые необходимо создать
     * @return true усли все файлы были успешно созданы или уже существуют; false если хотя бы один файл не был создан
     */
    public static boolean createFiles(String[] fileNames) {
        boolean allFilesWereCreated = true;
        for (String fileName : fileNames) {
            boolean createSucces = FileUtils.createFile(fileName);

            if (!createSucces) {
                allFilesWereCreated = false;
            }
        }

        return allFilesWereCreated;
    }

    /**
     * Вернуть содержимое файла с заданным именем
     *
     * @param fileName имя файла, содержимое которого необходимо вернуть
     * @return null если файл с заданным именем пуст; fileText.toString() если файл не пуст.
     * @throws FileNotFoundException если {@code File(fileName)} не существует
     */
    public static String readFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
            return null;
        }

        StringBuilder fileText = new StringBuilder();
        while (scanner.hasNext()) {
            fileText.append(scanner.next());
        }

        return fileText.toString();
    }

    /**
     * Записать в файл с заданным именнем переданный текст
     *
     * @param fileName имя файла, в который записывается текст
     * @param text текст, который записывается в файл с заданным именем или заменяет текст, который уже содержится в файле
     * @return false если файл с таким именем не найден; true если в файл успешно записан текст
     * @throws FileNotFoundException если {@code File(fileName)} не существует
     */
    public static boolean writeFile(String fileName, String text) {
        createFile(fileName);

        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(text);
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + fileName + " не найден");
            return false;
        }

        return true;
    }

    /**
     *Записать в файл с заданным именнем переданный текст
     *
     * @param fileName имя файла, в который записывается текст
     * @param text текст, который записывается в файл с заданным именем
     * @return " " + text, если файл с заданным именнем был пуст, а переданный текст успешно записан; fileText + text если файд с заданным именем уже содержал текст, а переданный текст успешно записан
     */
    public static boolean appendFile(String fileName, String text) {
        String fileText = readFile(fileName);
        if (fileText == null) {
            fileText = "";
        }

        return writeFile(fileName, fileText + text);
    }
}
