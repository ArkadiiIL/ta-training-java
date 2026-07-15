package com.epam.training.student_arkadii_ilinov.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage extends BasePage {
    @FindBy(css = "h2[data-test='complete-header']")
    private WebElement successMessage;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getCompleteMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText().strip();
    }
}


