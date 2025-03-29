package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleInput;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.entity.builders.ProductBuilder;
import org.example.managers.CollectionManager;

import java.util.HashMap;

public class ReplaceIfLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;

    public ReplaceIfLowerCommand(CollectionManager collectionManager, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        super("replace_if_lower", "заменить значение по ключу, если новое значение меньше старого");
        this.consoleInput = consoleInput;
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 аргумент (ключ)");
            return;
        }
        String key = args[0];
        HashMap<String, Product> collection = CollectionManager.getCollection();
        if (collection.isEmpty()) {
            consoleOutput.println("коллекция пуста");
            return;
        }
        if (!collection.containsKey(key)) throw new IllegalArgumentException("Нет такого ключа \"" + key + "\"");
        Product newProduct = new ProductBuilder(consoleOutput, consoleInput).build();
        if (newProduct.compareTo(collection.get(key)) < 0) {
            collectionManager.insertElement(key, newProduct);
            consoleOutput.println("Новый элемент меньше старого, поэтому он занял его место");
        }
        else {
            consoleOutput.println("Новый элемент оказался больше старого. Изменений не зафиксировано");
        }
    }
}
