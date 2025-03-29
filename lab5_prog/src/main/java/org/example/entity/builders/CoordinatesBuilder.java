package org.example.entity.builders;

import org.example.entity.Coordinates;
import org.example.utils.InputReader;
import org.example.utils.Printable;

import java.util.function.Predicate;

/**
 * Класс билдера для объектов класса Coordinates
 */
public class CoordinatesBuilder extends Builder<Coordinates> {
    public CoordinatesBuilder(Printable consoleOutput, InputReader consoleInput) {
        super(consoleOutput, consoleInput);
    }

    @Override
    public Coordinates build() {
        Predicate<Double> validateX = (x) -> (true);
        Predicate<Double> validateY = (y) -> (y != null);

        consoleOutput.println("Создание объекта Coordinates");

        double x = askDouble("координата x", "дробное число типа Double", validateX, "Неверный формат ввода!!");
        Double y = askDouble("координата y", "дробное число типа Double; значение не пусто;", validateY, "Неверный формат ввода: число должно удовлетворять требованиям");

        return new Coordinates(x, y);
    }
}