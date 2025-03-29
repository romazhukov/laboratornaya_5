package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterGreaterThanPartNumberCommand extends Command {
    private final ConsoleOutput consoleOutput;

    public FilterGreaterThanPartNumberCommand(ConsoleOutput consoleOutput) {
        super("filter_greater_than_part_number", "вывести количество элементов, значение поля manufactureCost которых меньше заданного");
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 аргумент");
            return;
        }
        String argPartNumber = args[0];

        HashMap<String, Product> filteredCollection = CollectionManager.getCollection()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getPartNumber().compareTo(argPartNumber) > 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,      // entry -> entry.getKey()
                        Map.Entry::getValue,    // entry -> entry.getValue()
                        (e1, e2) -> e1,         // при совпадении ключей берем первый (такого почти не бывает)
                        HashMap::new)
                );

        if (filteredCollection.isEmpty()) {
            consoleOutput.println("Элементов удовлетворяющих условию не найдено");
            return;
        }
        filteredCollection
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> consoleOutput.println("[KEY: " + entry.getKey() + "] " + entry.getValue()));
    }
}
