package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.managers.CollectionManager;
import org.example.utils.Validatable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Модель Product
 */
@Getter
public class Product implements Validatable, Comparable<Product> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Setter
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Setter
    private Coordinates coordinates; //Поле не может быть null

    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Setter
    private long price; //Значение поля должно быть больше 0

    @Setter
    private String partNumber; //Длина строки не должна быть больше 83, Значение этого поля должно быть уникальным, Поле не может быть null

    @Setter
    private float manufactureCost;

    @Setter
    private UnitOfMeasure unitOfMeasure; //Поле может быть null

    @Setter
    private Person owner; //Поле не может быть null

    public Product(String name, Coordinates coordinates, long price, String partNumber, float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;

        this.id = CollectionManager.generateFreeId();
        this.creationDate = new Date();
    }

    @Override
    public boolean validate() {
        return (
                id != null && id > 0                // TODO: uniqueCheck
                && name != null && !name.isBlank()
                && coordinates != null
                && creationDate != null
                && price > 0
                && partNumber.length() > 83         // TODO: uniqueCheck
                && owner != null
                && coordinates.validate()
                && owner.validate()
        );
    }

    @Override
    public int compareTo(Product product) {
        return this.creationDate.compareTo(product.creationDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return String.format(
                "Product:\n" +
                "- id              | %d\n" +
                "- name            | %s\n" +
                "- coordinates     | %s\n" +
                "- creationDate    | %s\n" +
                "- price           | %d\n" +
                "- partNumber      | %s\n" +
                "- manufactureCost | %.2f\n" +
                "- unitOfMeasure   | %s\n" +
                "- owner           | %s",
                id,
                name,
                coordinates,
                dateFormat.format(creationDate),
                price,
                partNumber,
                manufactureCost,
                unitOfMeasure != null ? unitOfMeasure.name() : "?",
                owner
        );
    }
}