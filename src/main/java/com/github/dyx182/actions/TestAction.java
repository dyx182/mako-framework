package com.github.dyx182.actions;

import com.github.dyx182.core.TestContext;

import java.util.Map;

public interface TestAction {

    /**
     * Возвращает имя действия (используется в yaml)
     */
    String getName();

    /**
     * Выполнение действия
     *
     * @param context Контекст теста (локаторы, переменные)
     * @param params  Параметры из yaml
     */
    void execute(TestContext context, Map<String, Object> params);

    /**
     * Валидация параметра
     * @param params параметры из yaml
     */
    default void validateParams(Map<String, Object> params) {
        if (!params.containsKey("element")) {
            throw new IllegalArgumentException("Expected parameter - element");
        }
    }
}
