package com.github.dyx182.actions;

import com.github.dyx182.core.TestContext;

import java.util.Map;

public interface TestAction {

    /**
     * Возвращает имя действия (используется в YAML)
     */
    String getName();

    /**
     * Выполнение действия
     *
     * @param context Контекст теста (локаторы, переменные)
     * @param params  Параметры из YAML файла
     */
    void execute(TestContext context, Map<String, Object> params);

    /**
     * Валидация параметра
     */
    default void validateParams(Map<String, Object> params) {
        if (!params.containsKey("element")) {
            throw new IllegalArgumentException("Expected parameter - element");
        }
    }
}
