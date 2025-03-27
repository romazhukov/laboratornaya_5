package org.example.command;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.InputManager;
import org.example.utils.InputReader;

import java.util.Scanner;

/**
 * Класс для пользовательского ввода
 */
public class ConsoleInput implements InputReader {
    private static final Scanner scanner = InputManager.scanner;
    @Getter
    @Setter
    private static boolean fileMode = false;

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

}