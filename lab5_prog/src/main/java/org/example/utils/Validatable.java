package org.example.utils;

/**
 * Интерфейс реализующий метод validate()
 */
public interface Validatable {
    /**
     * Валидация сущности
     * @return true если все ок, false если что-то не ок
     */
    boolean validate();
}
