package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleInput;
import org.example.command.ConsoleOutput;
import org.example.entity.builders.ProductBuilder;
import org.example.managers.CollectionManager;

public class InsertCommand extends Command {
    private ConsoleOutput consoleOutput;
    private ConsoleInput consoleInput;
    private CollectionManager collectionManager;

    public InsertCommand(ConsoleOutput consoleOutput, ConsoleInput consoleInput, CollectionManager collectionManager) {
        super("insert", "insert [key] {element} - добавить новый элемент в коллекцию");
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 аргумент - ключ, под которым необходимо вставить новую запись");
            return;
        }
        consoleOutput.println("Вставка нового объекта в коллекцию под ключом \"" + args[0] + "\". Введите необходимые данные");
        collectionManager.insertElement(args[0], new ProductBuilder(consoleOutput, consoleInput).build());
        consoleOutput.println("Объект успешно добавлен в коллекцию под ключом \"" + args[0] + "\"");
    }
}
