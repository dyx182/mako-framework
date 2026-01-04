package com.github.dyx182.actions.impl;

import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.dls.TestDSL;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

import java.util.Map;

@AllArgsConstructor
public class SetAction implements TestAction {

    private final LocatorManager locatorManager;

    @Override
    public String getName() {
        return "Set";
    }

    @Override
    public void validateParams(Map<String, Object> params) {
        if (!params.containsKey("element")) {
            throw new IllegalArgumentException("Expected parameter - element");
        }
        if (!params.containsKey("text")) {
            throw new IllegalArgumentException("Expected parameter to set - text");
        }
    }

    @Override
    public void execute(TestContext context, Map<String, Object> params) {
        validateParams(params);
        String elementName = (String) params.get("element");
        String text = (String) params.get("text");
        By locator = locatorManager.getLocator(context, elementName);
        TestDSL.set(locator, text);

        //todo добавить прокидку в переменную введенного текста и добавить поддержку переменных для вводимого текста
    }
}
