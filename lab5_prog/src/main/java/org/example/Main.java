package org.example;

import org.example.command.*;
import org.example.managers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static ConsoleOutput consoleOutput = new ConsoleOutput();
    static ConsoleInput consoleInput = new ConsoleInput();
    static CollectionManager collectionManager = new CollectionManager();
    static CommandManager commandManager = new CommandManager();

    public static void main(String[] args) {
        if (!validateArgs(args)) return;

        FileManager fileManager = new FileManager(new File(args[0]), consoleOutput);

        if (!fileManager.validate()) return;

        RuntimeManager runtimeManager = new RuntimeManager(consoleOutput, consoleInput, commandManager, fileManager);
        runtimeManager.run();
    }

    public static boolean validateArgs(String[] args) {
        if (args.length == 0) {
            consoleOutput.printError("Вы не ввели путь файла.");
            return false;
        }
        else if (args.length > 1) {
            consoleOutput.printError("Программа принимает 1 аргумент.");
            return false;
        }
        return true;
    }
}

