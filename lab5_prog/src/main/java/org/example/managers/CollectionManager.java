package org.example.managers;

import lombok.Getter;
import org.example.entity.Product;
import org.example.utils.exceptions.ValidationError;

import java.util.*;

@Getter
public class CollectionManager {
    /**
     * Коллекция объектов
     */
    @Getter
    private static HashMap<String, Product> collection = new HashMap<>();

    /**
     * Время инициализации коллекции
     * Время инициализации объекта CollectionManager
     */
    private final Date initDate = new Date();

    /**
     * Метод присваивает коллекции передаваемое значение, если элементы коллекции корректны
     * @param collection новая коллекция
     * @return true если успешно, false если не прошла валидация одного из элементов
     */
    public static boolean setCollection(HashMap<String, Product> collection) {
        if (!CollectionManager.allIdsAreUnique(collection)) {
            return false;
        }

        for (Product p : collection.values()) {
            if (!p.validate()) {
                return false;
            }
        }
        CollectionManager.collection = collection;
        return true;
    }

    /**
     * Статический метод для генерации нового id
     * @return минимальный несуществующий id
     */
    public static long generateFreeId() {
        if (collection.isEmpty()) return 1;

        HashSet<Long> existIds = new HashSet<>();
        for (Product product : collection.values()) {
            existIds.add(product.getId());
        }

        for (long i = 1; i < Collections.max(existIds); i++) {
            if (!existIds.contains(i)) return i;
        }
        return Collections.max(existIds) + 1;
    }

    /**
     * Получение типа коллекции
     * @return класс объекта коллекции
     */
    public String getTypeOfCollection() {
        return collection.getClass().getName();
    }

    /**
     * Возвращает размер коллекции
     * @return число элементов в коллекции
     */
    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * Находит объект в коллекции по его id
     * @param id айди.
     * @return Объект из коллекции или null, если его не существует
     */
    public Product getElementById(Long id) {
        for (Product product : collection.values()) {
            if (Objects.equals(product.getId(), id)) return product;
        }
        return null;
    }

    /**
     * Метод для валидации ключа хэшмапа
     * @param key ключ
     * @return OK => true, !OK => false
     */
    public static boolean validateKey(String key) {
        return !key.isBlank();
    }

    /**
     * Добавляет элемент в коллекцию предварительно проведя контрольную валидацию
     * @param product новый элемент
     * @throws ValidationError в случае неудачного прохождения валидации
     */
    public void insertElement(String key, Product product) throws ValidationError, IllegalArgumentException {
        if (!validateKey(key)) throw new IllegalArgumentException("Ключ не может быть пустым");
        if (!product.validate()) {
            throw new ValidationError(product);
        }
        collection.put(key, product);
    }

    /**
     * Очищает коллекцию
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Удаляет элемент из коллекции по его ключу
     * @param key ключ
     * @return true если элемент с таким ключом есть и удален, и false если элемент не найден
     */
    public boolean removeByKey(String key) {
        return collection.remove(key) != null;
    }

    /**
     * Проверка, являются ли все id в коллекции уникальными
     * @param collection
     * @return true если все id уникальны, false если есть повторения
     */
    public static boolean allIdsAreUnique(HashMap<String, Product> collection) {
        HashSet<Long> ids = new HashSet<>();
        for (Product p : collection.values()) {
            if (ids.contains(p.getId())) return false;
            ids.add(p.getId());
        }
        return true;
    }

    /**
     * Удаление элемента по его id
     * @param id id
     * @return ключ по которому произошло изменение иначе null
     */
    public String removeById(long id) {
        Iterator<Map.Entry<String, Product>> iterator = collection.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Product> entry = iterator.next();
            if (entry.getValue().getId() == id) {
                iterator.remove();
                return entry.getKey();
            }
        }
        return null;
    }
}
