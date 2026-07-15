package com.epam.training.student_arkadii_ilinov.pages;

import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    @FindBy(css = "input[data-test='username']")
    private WebElement usernameField;
    @FindBy(css = "input[data-test='password']")
    private WebElement passwordField;
    @FindBy(css = "input[data-test='login-button']")
    private WebElement loginButton;
    private final static String URL = ConfigReader.getBaseUrl();

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the login page")
    public LoginPage open() {
        driver.navigate().to(URL);
        return this;
    }

    @Step("Login as {username} with password {password}")
    public InventoryPage login(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new InventoryPage(driver);
    }
}
