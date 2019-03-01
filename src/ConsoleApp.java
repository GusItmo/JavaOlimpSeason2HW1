import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ConsoleApp {

    private static final String OUTPUT_FILE_NAME = "output.txt";

    public static void main(String[] args) {
        boolean createSuccess = FileUtils.createFile(OUTPUT_FILE_NAME);
        if (!createSuccess) {
            System.err.println("Не удалось создать файл " + OUTPUT_FILE_NAME);
            System.exit(-1);
        }

        // Файл, в который будем выводить результат выполнения некоторых наших команд
        File outputFile = new File(OUTPUT_FILE_NAME);

        // try-with-resources блок. Почитайте в интернете про него, очень интересная штука
        try (PrintStream out = new PrintStream(new FileOutputStream(outputFile))) {
            Scanner scanner = new Scanner(System.in);

            // При запуске консольного приложения выведем пользователю справку, чтобы он понимал что нужно вводить
            printHelp();

            while (true) {
                String line = scanner.nextLine(); // print Hello world
                String[] data = line.split(" "); // { "print", "Hello", "world" }

                // Если ничего не ввели, то продолжаем обрабатывать команды с нового витка while
                if (data[0].isEmpty()) {
                    continue;
                }

                String command = data[0]; // print

                try {
                    String text;
                    switch (command) {
                        // Записать в файл аргумент команды
                        case "print":
                            text = line.substring(data[0].length() + 1); // Hello world
                            out.print(text);

                            System.out.println("Аргумент команды был успешно записан в файл ");
                            break;

                        // Записать в файл аргумент команды, добавив в конце символ перевода строки
                        case "println":
                            text = line.substring(data[0].length() + 1); // Hello world
                            out.println(text); // Hello world\n (\n - символ перевода строки)

                            System.out.println("Аргумент команды был успешно записан в файл ");
                            break;

                        case "read":
                            // TODO: реализуйте пожалуйста сами. Нужно воспользоваться классом FileUtils
                            FileUtils.readFile(OUTPUT_FILE_NAME);
                            System.out.println(FileUtils.readFile(OUTPUT_FILE_NAME));
                            break;

                        case "touch":
                            // TODO: реализуйте пожалуйста сами. Нужно воспользоваться классом FileUtils
                            text = line.substring(data[0].length() + 1);
                            FileUtils.createFile(text);
                            System.out.println("Файл " + text + " успешно создан");
                            break;

                        // Напечатать справку по консольному приложению
                        case "help":
                            printHelp();
                            break;

                        // Выйти из консольного приложения. Можно выйти несколькими способами: quit, exit, q
                        case "quit":
                        case "exit":
                        case "q":
                            scanner.close();
                            out.close();
                            System.exit(0);

                            // Если пользователь ввел команду, которой нет, то пакажем ему справку
                        default:
                            printHelp();
                    }
                } catch (Exception ex) { // Если возникает какое-либо исключение во время выполнения команды, то обрабатываем его
                    // Выводим в поток ошибок текст ошибки
                    System.err.println("Во время выполнении команды " + command + " произошла ошибка: " + ex.getMessage());
                }

            }
        } catch (FileNotFoundException ex) {
            System.err.println("Файл " + OUTPUT_FILE_NAME + " не был найден! Создайте его и попробуйте еще раз.");
        }
    }

    private static void printHelp() {
        System.out.println("print {text} - вывести {text} в файл");
        System.out.println("println {text} - вывести {text} в файл с добавлением символа перевода строки");
        System.out.println("read {filename} - прочитать содержимое файла {filename} и вывести его в консоль");
        System.out.println("touch {filename} - создать файл {filename}");
        System.out.println("help - отобразить данное сообщение");
        System.out.println("exit - выйти из данного приложения");
    }
}
