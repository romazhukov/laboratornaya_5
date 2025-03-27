package org.example.managers;

import lombok.Getter;
import org.example.command.Command;

import java.util.Collection;
import java.util.HashMap;

/**
 * Менеджер для управления доступными командами
 */
public class CommandManager {
    @Getter
    private final HashMap<String, Command> commands = new HashMap<>();

    /**
     * Добавляет команды в коллекцию команд
     * @param command объект команды
     */
    public void addCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    /**
     * Добавляет коллекцию команд в колеекцию команд
     * @param commandCollection коллекция из добавляемых команд
     */
    public void addCommands(Collection<Command> commandCollection) {
        for (Command command : commandCollection) {
            addCommand(command);
        }
    }

    /**
     * Получение списка доступных команд
     * @return HashMap{commandName, command}
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    /**
     * Производит выполнение команды
     * @param name имя команды
     * @param args аргументы необходимые для выполнения команды
     */
    public void execute(String name, String[] args) {
        Command command = commands.get(name);
        command.execute(args);
    }
}