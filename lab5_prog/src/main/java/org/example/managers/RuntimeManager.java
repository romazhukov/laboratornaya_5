package org.example.managers;

import lombok.AllArgsConstructor;
import org.example.command.ConsoleOutput;
import org.example.utils.InputReader;
import org.example.utils.Printable;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RuntimeManager implements Runnable {
    private final Printable consoleOutput;
    private final InputReader consoleInput;
    private final CommandManager commandManager;
    private final FileManager fileManager;

    public RuntimeManager(Printable consoleOutput, InputReader consoleInput, CommandManager commandManager, FileManager fileManager) {
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
        this.commandManager = commandManager;
        this.fileManager = fileManager;
    }

    @Override
    public void run() {
        // обработка любого завершения программыы (ctrl + c)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consoleOutput.println("Программа завершает работу");
        }));

        consoleOutput.println(
                "Добро пожаловать! \n" +
                "Введите \"help\" для справки по доступным командам");
        consoleOutput.println("Файл коллекции: " + fileManager.getFile().getName());
        while (true) {
            try {
                consoleOutput.print("$ ");
                String query = consoleInput.readLine().trim();
                String[] queryParts = query.split(" ");
                launchCommand(queryParts, commandManager, consoleOutput);
            } catch (NoSuchElementException e) {  // ^D
                consoleOutput.println("Пользователь завершил ввод");
                break;
            } catch (Exception e) {
                consoleOutput.printError("Ошибка во время выполнения: " + e.getMessage());
                break;
            }
        }
    }

    /**
     * Метод, проверяющий корректность команды и запускающий ее
     * @param queryParts массив содержащий части команды
     * @param commandManager объект командного менеджера
     * @param consoleOutput объект класса, реализующего вывод
     */
    public static void launchCommand(String[] queryParts, CommandManager commandManager, Printable consoleOutput) {
        String qCommandName = queryParts[0];
        if (qCommandName.isBlank()) return;
        String[] qCommandArgs = Arrays.copyOfRange(queryParts, 1, queryParts.length);
        if (!commandManager.getCommands().containsKey(qCommandName)) {
            consoleOutput.printError(String.format("Команда \"%s\" не найдена. Введите \"help\", если возникли трудности", qCommandName));
            return;
        }
        try {
            commandManager.getCommands().get(qCommandName).execute(qCommandArgs);
        } catch (NoSuchElementException ignored) {
            // Ctrl + D
        } catch (Exception e) {
            consoleOutput.printError("Ошибка во время выполнения команды \"" + qCommandName + "\": " + e.getMessage());
        }

    }
}