package org.example.command.commands;

import org.example.command.Command;
import org.example.command.ConsoleOutput;

/**
 * Класс команды exit
 */
public class ExitCommand extends Command {
    private final ConsoleOutput consoleOutput;

    public ExitCommand(ConsoleOutput consoleInput) {
        super("exit", "выход без сохранения");
        this.consoleOutput = consoleInput;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            consoleOutput.printError("Команда не принимает аргументов");
            return;
        }
        System.exit(0);
    }
}