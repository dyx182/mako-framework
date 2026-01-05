package com.github.dyx182.actions.impl;

import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.dls.TestDSL;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

import java.util.Map;

@AllArgsConstructor
public class AssertTextAction implements TestAction {

    LocatorManager locatorManager;

    @Override
    public String getName() {
        return "assert_text";
    }

    @Override
    public void validateParams(Map<String, Object> params) {
        if(!params.containsKey("element") || !params.containsKey("expected_text")) {
            throw new IllegalArgumentException("Expected parameter - element or expected_text");
        }
    }

    @Override
    public void execute(TestContext context, Map<String, Object> params) {
        validateParams(params);
        String elementName = (String) params.get("element");
        By locator = locatorManager.getLocator(context, elementName);
        String expectedText = (String) params.get("expected_text");
        TestDSL.assertText(expectedText, locator);
    }
}
