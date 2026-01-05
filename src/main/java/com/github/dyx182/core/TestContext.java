package com.github.dyx182.core;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * TestContext - центральное хранилище данных теста.
 */
@ToString
public class TestContext {

    /**
     * В переменных хранятся все данные для одного конкретного теста.
     */
    private final Map<String, String> locators = new HashMap<>();
    private final Map<String, Object> variables = new HashMap<>();

    public void addLocator(String page, String elementName, String locator) {
        String key = page + "." + elementName;
        locators.put(key, locator);
    }

    /**
     * Загрузить локаторы страницы
     */
    public void loadPage(String pageName, Map<String, String> pageLocators) {
        if (pageLocators != null) {
            for (Map.Entry<String, String> entry : pageLocators.entrySet()) {
                String key = pageName + "." + entry.getKey();
                locators.put(key, entry.getValue());
            }
        }
    }

    /**
     * Получить локатор по полному пути "page.element"
     */
    public String getLocator(String fullPath) {
        String locator = locators.get(fullPath);
        if (locator == null) {
            throw new RuntimeException("Locator not found: '" + fullPath +
                    "'. Available: " + locators.keySet());
        }
        return locator;
    }

    /**
     * Загрузить несколько локаторов сразу (из YAML файла).
     */
    public void loadLocators(String page, Map<String, String> newLocators) {
        if (newLocators != null) {
            for (Map.Entry<String, String> entry : newLocators.entrySet()) {
                String key = page + "." + entry.getKey();
                this.locators.put(key, entry.getValue());
            }
        }
    }

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
