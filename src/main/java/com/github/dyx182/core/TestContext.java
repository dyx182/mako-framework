package com.github.dyx182.core;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class TestContext {


    /**
     * Хранит все данные для ОДНОГО конкретного теста.
     */

    private final Map<String, String> locators = new HashMap<>();

    private final Map<String, Object> variables = new HashMap<>();

    /**
     * Добавить локатор в контекст.
     */
    public void addLocator(String elementName, String locator) {
        locators.put(elementName, locator);
    }

    /**
     * Получить локатор из контекста.
     */
    public String getLocator(String elementName) {
        String locator = locators.get(elementName);
        if (locator == null) {
            throw new RuntimeException("locator '" + elementName + "' not found in context");
        }
        return locator;
    }

    /**
     * Загрузить несколько локаторов сразу (из YAML файла).
     */
    public void loadLocators(Map<String, String> newLocators) {
        if (newLocators != null) {
            locators.putAll(newLocators);
        }
    }

    /**
     * Проверить, есть ли локатор в контексте.
     */
    public boolean hasLocator(String elementName) {
        return locators.containsKey(elementName);
    }

    public void setVariable(String name, Object object) {
        variables.put(name, object);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public String getStringVariable(String name) {
        Object value = variables.get(name);
        return value != null ? value.toString() : null;
    }

    public void clear() {
        locators.clear();
        variables.clear();
    }
}
