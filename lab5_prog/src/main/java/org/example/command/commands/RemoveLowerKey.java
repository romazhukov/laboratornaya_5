package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleInput;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда remove_lower_key
 * Удаляет все элементы коллекции ключ которыз меньше чем заданный
 * ВАЖНО:   по моей логике все будет работать и без наличия этого ключа в коллекции
 *          просто произойдет "фильтр" ключей по этмоу ключу
 */
public class RemoveLowerKey extends Command {
    private final ConsoleOutput consoleOutput;

    public RemoveLowerKey(ConsoleOutput consoleOutput) {
        super("remove_lower_key", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            consoleOutput.printError("Команда принимает 1 аргумент (ключ)");
            return;
        }
        String key = args[0];
        if (CollectionManager.getCollection().isEmpty()) {
            consoleOutput.println("Коллекция пуста");
            return;
        }
        HashMap<String, Product> newCollection = CollectionManager.getCollection()
                .entrySet()
                .stream()
                .filter(entry -> (entry.getKey().compareTo(key) >= 0))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,      // entry -> entry.getKey()
                        Map.Entry::getValue,    // entry -> entry.getValue()
                        (e1, e2) -> e1,         // при совпадении ключей берем первый (такого почти не бывает)
                        HashMap::new)
                );
        CollectionManager.setCollection(newCollection);

        if (CollectionManager.getCollection().size() == newCollection.size()) consoleOutput.println("Коллекция не была тронута");
        else consoleOutput.println("Успешно. В коллекции осталось элементов: " + newCollection.size());

    }
}
