package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.utils.Validatable;

/**
 * Модель Person
 */
@Getter
@Setter
public class Person implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Строка не может быть пустой, Поле не может быть null
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null

    public Person(String name, String passportID, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    @Override
    public boolean validate() {
        return (
                name != null && !name.isBlank()
                && passportID != null && !passportID.isBlank()
                && eyeColor != null
                && hairColor != null
                && nationality != null
        );
    }

    @Override
    public String toString() {
        return String.format(
                "Person (name: %s; passportID: %s; eyeColor: %s; hairColor: %s; nationality: %s)",
                name, passportID, eyeColor, hairColor, nationality
        );
    }
}