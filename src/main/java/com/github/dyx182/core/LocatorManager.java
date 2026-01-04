package com.github.dyx182.core;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class LocatorManager {

    private final Yaml yaml = new Yaml();

    /**
     * Загружает локаторы из YAML файла и помещает их в контекст теста.
     *
     * @param context Контекст теста (куда загружать)
     * @param filePath Путь к YAML файлу с локаторами
     */
    public void loadIntoContext(TestContext context, String filePath) {
        System.out.println("loading locators from file " + filePath);

        try(InputStream inputStream = new FileInputStream(filePath)) {

            Map<String, String> loadedLocators = yaml.load(inputStream);

            if (loadedLocators != null && !loadedLocators.isEmpty()) {
                context.loadLocators(loadedLocators);
                System.out.println("loaded locators " + loadedLocators.size());
            } else {
                System.out.println("file with locators is empty");
            }

        } catch (Exception e) {
            System.err.println("error " + e.getMessage());
            throw new RuntimeException("locators not loaded from " + filePath, e);
        }

    }

    /**
     * Получает By объект для элемента по его имени.
     *
     * @param context Контекст теста (откуда брать)
     * @param elementName Имя элемента из шага теста
     * @return By объект для поиска элемента
     */
    public By getLocator(TestContext context, String elementName) {
        try {
            String locatorString = context.getLocator(elementName);
            By locator = LocatorResolver.resolve(locatorString);
            System.out.println("locator defined " + elementName + ": " + locatorString);
            return locator;
        } catch (Exception e) {
            System.err.println("locator not defined to element " + elementName);
            throw e;
        }
    }

    /**
     * Добавляет новый локатор в контекст (для динамических элементов).
     */
    public void addLocator(TestContext context, String elementName, String locatorString) {
        context.addLocator(elementName, locatorString);
        System.out.println("added locator: " + elementName + " = " + locatorString);
    }
}
