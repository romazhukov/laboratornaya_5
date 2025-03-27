package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;
import org.example.managers.CollectionManager;

import java.text.SimpleDateFormat;

/**
 * Класс команды info
 */
public class InfoCommand extends Command {
    private final ConsoleOutput consoleOutput;
    private final CollectionManager collectionManager;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public InfoCommand(ConsoleOutput consoleOutput, CollectionManager collectionManager) {
        super("info", "вывод в стандартный поток вывода информации о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            consoleOutput.printError("Команда не принимает аргументов!!");
            return;
        }
        consoleOutput.println("Информация о коллекции:");
        consoleOutput.println(String.format(
                        "- тип                   %s\n" +
                        "- количество элементов  %d\n" +
                        "- дата инициализации    %s",
                        collectionManager.getTypeOfCollection(),
                        collectionManager.getCollectionSize(),
                        dateFormat.format(collectionManager.getInitDate())
                )
        );
    }
}