package com.epam.training.student_arkadii_ilinov.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class BasePage {
    protected final WebDriver driver;
    private final WebDriverWait wait;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement clickable(By locator) {
        return wait.until(elementToBeClickable(locator));
    }

    protected WebElement visible(By locator) {
        return wait.until(visibilityOfElementLocated(locator));
    }

    protected List<WebElement> allVisible(By locator) {
        return wait.until(visibilityOfAllElementsLocatedBy(locator));
    }
}
