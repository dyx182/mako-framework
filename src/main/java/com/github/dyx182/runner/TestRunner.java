package com.github.dyx182.runner;

import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.dls.TestDSL;
import com.github.dyx182.model.TestCase;
import com.github.dyx182.model.TestStep;
import com.github.dyx182.parser.ParserYaml;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestRunner {

    private final ParserYaml parser = new ParserYaml();
    private final LocatorManager locatorManager = new LocatorManager();
    private final TestContext context = new TestContext();

    /**
     * Запускает тест из YAML файла.
     *
     * @param testFilePath     Путь к YAML файлу теста
     * @param locatorsFilePath Путь к YAML файлу локаторов
     */
    public void runTest(String testFilePath, String locatorsFilePath) {

        try {
            locatorManager.loadIntoContext(context, locatorsFilePath);

            TestCase testCase = parser.parseTest(testFilePath);
            executeTestCase(testCase);

        } finally {
            if (context != null) {
                context.clear();
            }
        }
    }

    /**
     * Выполняет все шаги теста.
     */
    private void executeTestCase(TestCase testCase) {
        for (TestStep step : testCase.getSteps()) {
            executeStep(step);
        }
    }

    /**
     * Выполняет один шаг теста.
     */
    private void executeStep(TestStep step) {
        String action = step.getAction();

        switch (action) {
            case "open":
                String url = step.getStringParam("url");
                TestDSL.open(url);
                break;

            case "type":
                By typeLocator = locatorManager.getLocator(context, step.getStringParam("element"));
                String text = resolveVariables(step.getStringParam("text"));
                TestDSL.set(typeLocator, text);
                break;

            case "click":
                By clickLocator = locatorManager.getLocator(context, step.getStringParam("element"));
                TestDSL.click(clickLocator);
                break;

            case "assert_text":
                //todo сделать метод для добавления ожидаемого текста в переменную,
                String expectedText = resolveVariables(step.getStringParam("expected"));
                By assertLocator = locatorManager.getLocator(context, step.getStringParam("element"));
                TestDSL.assertText(assertLocator);
                break;

            default:
                throw new RuntimeException("Неизвестное действие: " + action);
        }
    }

    /**
     * Заменяет переменные в строке.
     * Пример: "Заказ №{{order_id}} создан" → "Заказ №12345 создан"
     */
    private String resolveVariables(String text) {
        if (text == null) return null;

        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
        Matcher matcher = pattern.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String varName = matcher.group(1);
            Object value = context.getVariable(varName);
            String replacement = value != null ? value.toString() : "";
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
