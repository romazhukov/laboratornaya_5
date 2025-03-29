package org.example.entity.builders;

import org.example.entity.Country;
import org.example.entity.EyeColor;
import org.example.entity.HairColor;
import org.example.entity.Person;
import org.example.utils.InputReader;
import org.example.utils.Printable;

import java.util.function.Predicate;

public class PersonBuilder extends Builder<Person> {
    public PersonBuilder(Printable consoleOutput, InputReader consoleInput) {
        super(consoleOutput, consoleInput);
    }
    @Override
    public Person build() {
        Predicate<String> validateName = (name) -> (name != null && !name.isBlank());
        Predicate<String> validatePassportID = (passportID) -> (passportID != null && !passportID.isBlank());
        Predicate<EyeColor> validateEyeColor = (eyeColor) -> (eyeColor != null);
        Predicate<HairColor> validateHairColor = (hairColor) -> (hairColor != null);
        Predicate<Country> validateNationality = (nationality) -> (nationality != null);

        consoleOutput.println("Создание объекта Person");

        String name = askString("Имя", "Введите имя", validateName, "Имя не может быть пустым");
        String passportID = askString("Паспортный ID", "Введите ID", validatePassportID, "ID не может быть пустым");
        EyeColor eyeColor = askEnum("Цвет глаз", "Выберите цвет глаз", EyeColor.class, validateEyeColor, "Некорректный ввод");
        HairColor hairColor = askEnum("Цвет волос", "Выберите цвет волос", HairColor.class, validateHairColor, "Цвет волос не может быть null");
        Country nationality = askEnum("Национальность", "Выберите национальность", Country.class, validateNationality, "Некорректный ввод");

        return new Person(name, passportID, eyeColor, hairColor, nationality);

    }
}
