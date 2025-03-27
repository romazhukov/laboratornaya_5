package org.example.command;

/**
 * Базовый интерфейс для всех команд
 */
public interface CommandInterface {
    /**
     * Метод для запуска команды
     * @param args массив аргументов функции
     */
    void execute(String[] args);
}