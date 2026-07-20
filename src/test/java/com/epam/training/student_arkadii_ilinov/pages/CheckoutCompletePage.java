package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    private final static By SUCCESS_MESSAGE = By.cssSelector("h2[data-test='complete-header']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @Step("Get the order confirmation message")
    public String getCompleteMessage() {
        return visible(SUCCESS_MESSAGE).getText().strip();
    }
}