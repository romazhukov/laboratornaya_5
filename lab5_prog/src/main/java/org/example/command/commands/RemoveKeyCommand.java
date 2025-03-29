package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;
import org.example.managers.CollectionManager;

/**
 * Класс команды remove_by_id
 */
public class RemoveKeyCommand extends Command {
    private final ConsoleOutput consoleOutput;
    private final CollectionManager collectionManager;

    public RemoveKeyCommand(ConsoleOutput consoleOutput, CollectionManager collectionManager) {
        super("remove_key", "удаляет элемент из коллекции по его ключу");
        this.consoleOutput = consoleOutput;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 целочисленный аргумент");
            return;
        }
        String key = args[0].trim();
        if (collectionManager.removeByKey(key)) {
            consoleOutput.println("Элемент по ключу \"" + key + "\" был успешно удален из коллекции!!");
            return;
        }
        consoleOutput.println("Элемента по ключу \"" + key + "\" не существует");
    }
}