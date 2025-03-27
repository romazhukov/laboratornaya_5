package org.example.managers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.example.command.ConsoleOutput;
import org.example.entity.Product;
import org.example.utils.Validatable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

/**
 * Менеджер для управления файлами
 */
public class FileManager implements Validatable {
    @Getter
    private final File file;
    private final ConsoleOutput consoleOutput;

    public FileManager(File file, ConsoleOutput consoleOutput) {
        this.file = file;
        this.consoleOutput = consoleOutput;
    }

    /**
     * Статическая функция для получения расширения файла
     * @param file файл
     * @return расширение файла или null если он не имеет расширения
     */
    public static String getFileFormat(File file) {
        String name = file.getName();
        if (!name.contains(".")) return null;
        return name.substring(name.lastIndexOf('.') + 1);
    }

    @Override
    public boolean validate() {
        if (!file.exists()) {
            consoleOutput.printError("Файла, введенного в аргументе выполнения программы не существует.");
            return false;
        }
        if (!file.canRead() || !file.canWrite()) {
            consoleOutput.printError("Файл недоступен для чтения или для записи, программа может работать некорректно");
            return false;
        }
        if (!Objects.equals(getFileFormat(file), "xml")) {
            consoleOutput.printError("Программа работает только с файлами XML. Попробуйте выбрать другой файл.");
            return false;
        }
        return true;
    }

    /**
     * Сериализация коллекции в XML с помощью BufferedWriter
     * @param collection коллекция
     * @throws FileNotFoundException если файл не найден (программа гарантирует наличие файла)
     */
    public void serializeCollectionToXML(HashMap<String, Product> collection) throws FileNotFoundException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());

            writer.write(xmlMapper.writeValueAsString(collection));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Десериализация коллекции из XML с помощью InputStreamReader
     * Коллекция сохраняется в статической переменной CollectionManager.collection
     */
    public void deserializeCollectionFromXML() {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            StringBuilder xml = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                xml.append(line);
            }

            // проверяем пуст ли XML
            if (xml.toString().isEmpty()) {
                consoleOutput.printError("Файл XML пустой. Используется пустая коллекция.");
                CollectionManager.setCollection(new HashMap<>());
                return;
            }

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());

            HashMap<String, Product> xmlCollection = xmlMapper.readValue(
                    xml.toString(),
                    xmlMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Product.class)
            );

            if (!CollectionManager.setCollection(xmlCollection)) {
                throw new IOException("Одно или несколько полей не прошли валидацию");
            }
        } catch (IOException e) {
            consoleOutput.printError("Проверьте корректность .xml файла. Подробности ниже");
            consoleOutput.printError(e.getMessage());
            System.exit(-1);
        }
    }
}