package org.example.managers;

import lombok.Getter;
import org.example.command.ConsoleOutput;
import org.example.utils.Validatable;

import java.io.*;
import java.util.Objects;

/**
 * Менеджер для управления файлами
 */
public class FileManager implements Validatable {
    @Getter
    private final File file;
    private final ConsoleOutput consoleOutput;

    public FileManager(File file, ConsoleOutput consoleOutput) {
        this.file = file;
        this.consoleOutput = consoleOutput;
    }

    /**
     * Статическая функция для получения расширения файла
     * @param file файл
     * @return расширение файла или null если он не имеет расширения
     */
    public static String getFileFormat(File file) {
        String name = file.getName();
        if (!name.contains(".")) return null;
        return name.substring(name.lastIndexOf('.') + 1);
    }

    @Override
    public boolean validate() {
        if (!file.exists()) {
            consoleOutput.printError("Файла, введенного в аргументе выполнения программы не существует.");
            return false;
        }
        if (!file.canRead() || !file.canWrite()) {
            consoleOutput.printError("Файл недоступен для чтения или для записи, программа может работать некорректно");
            return false;
        }
        if (!Objects.equals(getFileFormat(file), "xml")) {
            consoleOutput.printError("Программа работает только с файлами XML. Попробуйте выбрать другой файл.");
            return false;
        }
        return true;
    }

    // сюда сериализацию впихнуть
}