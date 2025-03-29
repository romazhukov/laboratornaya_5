package org.example.command.commands;

import org.example.command.Command;
import org.example.entity.Product;
import org.example.managers.CollectionManager;
import org.example.utils.Printable;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс команды show
 */
public class ShowCommand extends Command {
    private final Printable consoleOutput;

    public ShowCommand(Printable consoleOutput) {
        super("show", "вывод всех элементов коллекции");
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            consoleOutput.printError("Команда не принимает аргументов!!");
            return;
        }
        // копируем чтобы удалить элементы в порядке приоритета
        HashMap<String, Product> collection = new HashMap<>(CollectionManager.getCollection());
        if (collection.isEmpty()) {
            consoleOutput.println("Коллекция пуста!!!");
            return;
        }
        consoleOutput.println("Элементы коллекции в порядке возростания приоритета:");
        collection.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> consoleOutput.println("[KEY: " + entry.getKey() + "] " + entry.getValue()));
    }
}