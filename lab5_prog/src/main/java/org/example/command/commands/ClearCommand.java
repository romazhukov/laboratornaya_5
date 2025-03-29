package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;
import org.example.managers.CollectionManager;

/**
 * Класс команды clear
 */
public class ClearCommand extends Command {
    private final ConsoleOutput consoleOutput;
    private final CollectionManager collectionManager;

    public ClearCommand(ConsoleOutput consoleOutput, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            consoleOutput.printError("Команда не принимает аргументов");
            return;
        }
        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.println("Коллекция итак пустая");
            return;
        }
        collectionManager.clearCollection();
        consoleOutput.println("Коллекция успешно очищена");
    }
}