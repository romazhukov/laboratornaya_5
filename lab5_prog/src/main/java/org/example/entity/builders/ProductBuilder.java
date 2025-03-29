package org.example.entity.builders;

import org.example.entity.Coordinates;
import org.example.entity.Person;
import org.example.entity.Product;
import org.example.entity.UnitOfMeasure;
import org.example.utils.InputReader;
import org.example.utils.Printable;

import java.util.function.Predicate;

public class ProductBuilder extends Builder<Product> {
    public ProductBuilder(Printable consoleOutput, InputReader consoleInput) {
        super(consoleOutput, consoleInput);
    }

    @Override
    public Product build() {
        Predicate<String> validateName = (name) -> (name != null && !name.isBlank());
        Predicate<Long> validatePrice = (price) -> (price > 0);
        Predicate<String> validatePartNumber = (partNumber) -> (partNumber != null && partNumber.length() > 83);
        Predicate<Float> validateManufactureCost = (manufactureCost) -> (true);
        Predicate<UnitOfMeasure> validateUnitOfMeasure = (unitOfMeasure) -> (true);

        consoleOutput.println("Создание объекта Product");

        String name = askString("Имя", "Введите имя продукта", validateName, "Имя не может быть пустым");
        Coordinates coordinates = new CoordinatesBuilder(consoleOutput, consoleInput).build();
        long price = askLong("Цена", "Введите цену", validatePrice, "Цена должна быть больше 0");
        String partNumber = askString("Номер детали", "Введите номер детали", validatePartNumber, "Номер не может быть null и должен быть длиннее 83 символов");
        float manufactureCost = askFloat("Затраты на производство", "Введите затраты", validateManufactureCost, "Некорректный ввод");
        UnitOfMeasure unitOfMeasure = askEnum("Единица измерения", "Выберите единицу измерения", UnitOfMeasure.class, validateUnitOfMeasure, "Некорректный ввод");
        Person owner = new PersonBuilder(consoleOutput, consoleInput).build();

        return new Product(name, coordinates, price, partNumber, manufactureCost, unitOfMeasure, owner);

    }

}
