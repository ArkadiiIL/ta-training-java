package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutOverviewPage extends BasePage {
    @FindBy(css = "button[data-test='finish']")
    private WebElement finishButton;
    @FindBy(css = "div[data-test='inventory-item-price']")
    private List<WebElement> priceItems;
    @FindBy(css = "div[data-test='subtotal-label']")
    private WebElement itemTotalPrice;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Finish checkout")
    public CheckoutCompletePage finishCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        return new CheckoutCompletePage(driver);
    }

    public List<BigDecimal> getItemsPrices() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(priceItems))
                .stream()
                .map(webElement -> webElement.getText().replace("$", "").strip())
                .map(BigDecimal::new)
                .toList();
    }

    public BigDecimal getItemTotalPrice() {
        String itemTotalPriceText =
                wait.until(ExpectedConditions.visibilityOf(itemTotalPrice))
                        .getText().replace("Item total: $", "").strip();
        return new BigDecimal(itemTotalPriceText);
    }
}
