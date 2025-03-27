package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.utils.Validatable;

/**
 * Модель Coordinates
 */
@Getter
@Setter
public class Coordinates implements Validatable {
    private double x;
    private Double y; //Поле не может быть null

    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public boolean validate() {
        return y != null;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}