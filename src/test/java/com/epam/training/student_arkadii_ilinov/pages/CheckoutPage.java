package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {
    @FindBy(css = "input[data-test='firstName']")
    private WebElement firstNameField;
    @FindBy(css = "input[data-test='lastName']")
    private WebElement lastNameField;
    @FindBy(css = "input[data-test='postalCode']")
    private WebElement postalCodeField;
    @FindBy(css = "input[data-test='continue']")
    private WebElement continueButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill checkout information: {firstName} {lastName}, {postalCode}")
    public CheckoutPage checkoutYourInformation(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(firstName);
        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
        wait.until(ExpectedConditions.elementToBeClickable(postalCodeField)).sendKeys(postalCode);
        return this;
    }

    @Step("Continue to checkout overview")
    public CheckoutOverviewPage continueCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return new CheckoutOverviewPage(driver);
    }
}
