package com.github.dyx182.core;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocatorManager {

    private final Yaml yaml = new Yaml();

    /**
     * Основной метод: загружает нужные страницы для теста
     */
    public void loadRequiredPages(TestContext context, String pagesFolder, List<String> requiredPages) {
        if (requiredPages == null || requiredPages.isEmpty()) {
            throw new RuntimeException("Test doesn't use any pages. " +
                    "Use 'page.element' format in test steps (e.g., 'login.username').");
        }

        List<String> loadedPages = new ArrayList<>();

        //todo remove it!

        // loadPageIfExists(context, pagesFolder, "common", loadedPages);

        for (String page : requiredPages) {
            loadPage(context, pagesFolder, page, loadedPages);
        }
    }

    private void loadPage(TestContext context, String folder, String pageName, List<String> loadedPages) {
        String filePath = folder + "/" + pageName + ".yml";
        File file = new File(filePath);

        if (!file.exists()) {
            throw new RuntimeException("Page file not found: " + filePath +
                    "\nRequired by test. Create file or fix page name in test.");
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            Map<String, String> pageLocators = yaml.load(inputStream);

            if (pageLocators == null || pageLocators.isEmpty()) {
                System.out.println("Page '" + pageName + "' is empty");
            } else {
                context.loadPage(pageName, pageLocators);
                loadedPages.add(pageName + " (" + pageLocators.size() + " locators)");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load page: " + pageName, e);
        }
    }

    private void loadPageIfExists(TestContext context, String folder, String pageName, List<String> loadedPages) {
        String filePath = folder + "/" + pageName + ".yml";
        File file = new File(filePath);

        if (file.exists()) {
            loadPage(context, folder, pageName, loadedPages);
        }
    }

    /**
     * Получить By локатор по полному пути "page.element"
     */
    public By getLocator(TestContext context, String elementPath) {
        try {
            if (!elementPath.contains(".")) {
                throw new IllegalArgumentException(
                        "Element must specify page: '" + elementPath + "'\n" +
                                "Use format: 'page.element' (e.g., 'login.username')"
                );
            }

            String locatorString = context.getLocator(elementPath);
            By locator = LocatorResolver.resolve(locatorString);

            System.out.println("Using locator: " + elementPath + " = " + locatorString);
            return locator;

        } catch (Exception e) {
            System.err.println("Failed to get locator: " + elementPath);
            throw e;
        }
    }
}
