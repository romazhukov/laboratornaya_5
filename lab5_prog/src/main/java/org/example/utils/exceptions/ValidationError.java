package org.example.utils.exceptions;

/**
 * Ошибка валидации
 */
public class ValidationError extends RuntimeException {
    public Object o;

    public ValidationError(Object o) {
        super();
        this.o = o;
    }

    @Override
    public String getMessage() {
        return "Ошибка при валидации объекта " + this.o.getClass().getName() + ".";
    }
}