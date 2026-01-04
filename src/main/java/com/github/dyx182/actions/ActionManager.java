package com.github.dyx182.actions;

import com.github.dyx182.actions.impl.ClickAction;
import com.github.dyx182.actions.impl.OpenAction;
import com.github.dyx182.actions.impl.SetAction;
import com.github.dyx182.core.LocatorManager;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {

    private final Map<String, TestAction> actionsRegistry = new HashMap<>();
    private final LocatorManager locatorManager = new LocatorManager();

    public ActionManager() {
        registerActions();
    }

    private void registerActions() {
        register(new ClickAction(locatorManager));
        register(new SetAction(locatorManager));
        register(new OpenAction());
    }

    public void register(TestAction action) {
        actionsRegistry.put(action.getName(), action);
        System.out.println("Registered action - " + action.getName());
    }

    public TestAction getAction(String actionName) {
        TestAction action = actionsRegistry.get(actionName);
        if(action==null) {
            throw new IllegalArgumentException("Unknown action");
        }
        return action;
    }
}
