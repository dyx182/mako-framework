package com.github.dyx182.actions.impl;

import com.github.dyx182.actions.TestAction;
import com.github.dyx182.core.TestContext;
import com.github.dyx182.dls.TestDSL;

import java.util.Map;

public class OpenAction implements TestAction {

    @Override
    public String getName() {
        return "Open";
    }

    @Override
    public void validateParams(Map<String, Object> params) {
        if (!params.containsKey("url")) {
            throw new IllegalArgumentException("Expected parameter - url");
        }
    }

    @Override
    public void execute(TestContext context, Map<String, Object> params) {
        validateParams(params);
        String url = (String) params.get("url");
        TestDSL.open(url);
    }
}
