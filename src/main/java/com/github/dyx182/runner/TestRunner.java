package com.github.dyx182.runner;

import com.github.dyx182.actions.ActionManager;
import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.model.TestCase;
import com.github.dyx182.model.TestStep;
import com.github.dyx182.parser.ParserYaml;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class TestRunner {

    private final ParserYaml parser;
    private final LocatorManager locatorManager;
    private final ActionManager actionManager;

    /**
     * Запуск теста из YAML файла.
     *
     * @param testFilePath     Путь к YAML файлу теста
     * @param locatorsFilePath Путь к YAML файлу локаторов
     */
    public void runTest(String testFilePath, String locatorsFilePath) {
        TestContext context = new TestContext();
        try {
            locatorManager.loadIntoContext(context, locatorsFilePath);
            TestCase testCase = parser.parseTest(testFilePath);
            executeTestCase(testCase, context);
            System.out.println("Test complete " + testCase.getName());

        } finally {
            if (context != null) {
                context.clear();
            }
        }
    }

    /**
     * Выполнение шагов теста.
     */
    private void executeTestCase(TestCase testCase, TestContext context) {
        for (TestStep step : testCase.getSteps()) {
            executeStep(step, context);
        }
    }

    /**
     * Выполнение шага теста.
     */
    private void executeStep(TestStep step, TestContext context) {
        String actionName = step.getAction();
        TestAction action = actionManager.getAction(actionName);
        action.execute(context, step.getParams());
    }
}
