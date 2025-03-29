package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.managers.CollectionManager;

import java.util.Map;
import java.util.stream.Stream;

/**
 * count_less_than_manufacture_cost
 */
public class CountLessThanManufactureCostCommand extends Command {
    private final ConsoleOutput consoleOutput;

    public CountLessThanManufactureCostCommand(ConsoleOutput consoleOutput) {
        super("count_less_than_manufacture_cost", "вывести количество элементов, значение поля manufactureCost которых меньше заданного");
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 аргумент - дробное число");
            return;
        }
        if (CollectionManager.getCollection().isEmpty()) {
            consoleOutput.println("Коллекция пуста");
            return;
        }
        try {
            float argManufactureCost = Float.parseFloat(args[0]);

            consoleOutput.println("Найдено записей: " +
                    CollectionManager.getCollection().entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().getManufactureCost() < argManufactureCost)
                    .count()
            );

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный аргумент - он должен быть дробным числом");
        }
    }
}
