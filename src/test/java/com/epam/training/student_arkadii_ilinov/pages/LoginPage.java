package com.epam.training.student_arkadii_ilinov.pages;

import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final static By USERNAME_FIELD = By.cssSelector("input[data-test='username']");
    private final static By PASSWORD_FIELD = By.cssSelector("input[data-test='password']");
    private final static By LOGIN_BUTTON = By.cssSelector("input[data-test='login-button']");
    private final static String URL = ConfigReader.getBaseUrl();

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the login page")
    public LoginPage open() {
        driver.navigate().to(URL);
        return this;
    }

    @Step("Login as {username}")
    public InventoryPage login(String username, @Param(mode = Parameter.Mode.MASKED) String password) {
        clickable(USERNAME_FIELD).sendKeys(username);
        clickable(PASSWORD_FIELD).sendKeys(password);
        clickable(LOGIN_BUTTON).click();
        return new InventoryPage(driver);
    }
}
