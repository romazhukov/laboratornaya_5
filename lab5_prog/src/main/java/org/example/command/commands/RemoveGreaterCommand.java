package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleInput;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.entity.builders.ProductBuilder;
import org.example.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда remove_greater
 * Удаляет все элементы, превышающие заданный (вводится вручную)
 */
public class RemoveGreaterCommand extends Command {
    private final CollectionManager collectionManager;
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;

    public RemoveGreaterCommand(CollectionManager collectionManager, ConsoleOutput consoleOutput, ConsoleInput consoleInput) {
        super("remove_greater", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 0) {
            consoleOutput.printError("Команда не принимает аргументов");
            return;
        }

        Product newProduct = new ProductBuilder(consoleOutput, consoleInput).build();
        HashMap<String, Product> toRemove = CollectionManager.getCollection()
                .entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(newProduct) >= 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,      // entry -> entry.getKey()
                        Map.Entry::getValue,    // entry -> entry.getValue()
                        (e1, e2) -> e1,         // при совпадении ключей берем первый (такого почти не бывает)
                        HashMap::new)
                );

        if (toRemove.isEmpty()) {
            consoleOutput.println("Элементов больше введенного не найдено");
            return;
        }

        toRemove.forEach((key, value) -> collectionManager.removeByKey(key));

        consoleOutput.println("Удалено записей: " + toRemove.size());
    }
}
