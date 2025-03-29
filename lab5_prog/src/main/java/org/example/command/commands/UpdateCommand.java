package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleInput;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.entity.builders.ProductBuilder;
import org.example.managers.CollectionManager;

/**
 * Класс команды update
 */
public class UpdateCommand extends Command {
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;
    private final CollectionManager collectionManager;

    public UpdateCommand(ConsoleInput consoleInput, ConsoleOutput consoleOutput, CollectionManager collectionManager) {
        super("update", "обновить элемент с введенным id");
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает только 1 целочисленный аргумент");
        }
        try {
            long id = Long.parseLong(args[0]);
            String key = collectionManager.removeById(id);
            if (key != null) {
                Product p = new ProductBuilder(consoleOutput, consoleInput).build();
                p.setId(id);
                collectionManager.insertElement(key, p);
                consoleOutput.println("Элемент с id=" + id + " был успешно изменен!!");
                return;
            }
            consoleOutput.printError("Элемента с id=" + id + " не существует");
        } catch (NumberFormatException e) {
            consoleOutput.printError("Команда принимает только 1 целочисленный аргумент");
        }
    }
}