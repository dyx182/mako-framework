package com.github.dyx182.actions.impl;

import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.LocatorManager;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.dls.TestDSL;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

import java.util.Map;

@AllArgsConstructor
public class ClickAction implements TestAction {

    private final LocatorManager locatorManager;

    @Override
    public String getName() {
        return "Click";
    }

    @Override
    public void execute(TestContext context, Map<String, Object> params) {
        validateParams(params);
        String elementName = (String) params.get("element");
        By locator = locatorManager.getLocator(context, elementName);
        TestDSL.click(locator);
        System.out.println("Execute click on element - " + elementName);
    }
}
