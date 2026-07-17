package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private final static By FIRST_NAME_FIELD = By.cssSelector("input[data-test='firstName']");
    private final static By LAST_NAME_FIELD = By.cssSelector("input[data-test='lastName']");
    private final static By POSTAL_CODE_FIELD = By.cssSelector("input[data-test='postalCode']");
    private final static By CONTINUE_BUTTON = By.cssSelector("input[data-test='continue']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill checkout information: {firstName} {lastName}, {postalCode}")
    public CheckoutPage checkoutYourInformation(String firstName, String lastName, String postalCode) {
        clickable(FIRST_NAME_FIELD).sendKeys(firstName);
        clickable(LAST_NAME_FIELD).sendKeys(lastName);
        clickable(POSTAL_CODE_FIELD).sendKeys(postalCode);
        return this;
    }

    @Step("Continue to checkout overview")
    public CheckoutOverviewPage continueCheckout() {
        clickable(CONTINUE_BUTTON).click();
        return new CheckoutOverviewPage(driver);
    }
}
