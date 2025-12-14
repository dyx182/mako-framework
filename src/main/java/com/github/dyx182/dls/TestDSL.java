package com.github.dyx182.dls;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.element;

public class TestDSL {

    public static void open(String url) {
        Selenide.open(url);
    }

    public static void set(By locator, String text) {
        element(locator).setValue(text);
    }

    public static void click(By locator) {
        element(locator).click();
    }

    public static void assertText(String expectedText, By locator) {
        Selenide.element(locator).shouldHave(text(expectedText));
    }

    public static void screenshot(String fileName) {
        Selenide.screenshot(fileName);
    }
}
