package com.github.dyx182.runner;

import com.github.dyx182.actions.ActionManager;
import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.model.TestCase;
import com.github.dyx182.model.TestStep;
import com.github.dyx182.parser.ParserYaml;

public class TestRunner {

    private final ParserYaml parser = new ParserYaml();
    private final LocatorManager locatorManager = new LocatorManager();
    private final ActionManager actionManager = new ActionManager();

    /**
     * Запуск теста из YAML файла.
     *
     * @param testFilePath Путь к YAML файлу теста
     * @param pagesFolder Папка с файлами страниц
     */
    public void runTest(String testFilePath, String pagesFolder) {
        TestContext context = new TestContext();
        try {
            TestCase testCase = parser.parseTest(testFilePath);
            locatorManager.loadRequiredPages(context, pagesFolder, testCase.getRequiredPages());
            executeTestCase(testCase, context);
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
