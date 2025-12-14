package com.github.dyx182.core;

import org.openqa.selenium.By;

public class LocatorResolver {

    /**
     * Определяет тип локатора и создает соответствующий By объект.
     *
     * @param locatorString Строка с локатором из YAML файла
     * @return By.cssSelector() или By.xpath()
     */

    public static By resolve(String locatorString) {
        if (locatorString == null || locatorString.trim().isEmpty()) {
            throw new IllegalArgumentException("Локатор не может быть пустым: " + locatorString);
        }

        String locator = locatorString.trim();

        if (locator.startsWith("//") || locator.startsWith(".//")) {
            return By.xpath(locator);
        }

        if (locator.startsWith("/") && !locator.contains("://")) {
            return By.xpath(locator);
        }

        if (locator.contains("/") &&
                (locator.contains("[") || locator.contains("]"))) {
            return By.xpath(locator);
        }

        if (locator.contains("text()") || locator.contains("@")) {
            return By.xpath(locator);
        }

        return By.cssSelector(locator);
    }
}
