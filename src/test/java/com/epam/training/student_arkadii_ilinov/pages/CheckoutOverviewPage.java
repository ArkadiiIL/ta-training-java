package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutOverviewPage extends BasePage {
    private final static By FINISH_BUTTON = By.cssSelector("button[data-test='finish']");
    private final static By PRICE_ITEMS = By.cssSelector("div[data-test='inventory-item-price']");
    private final static By ITEM_TOTAL_PRICE = By.cssSelector("div[data-test='subtotal-label']");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Step("Finish checkout")
    public CheckoutCompletePage finishCheckout() {
        clickable(FINISH_BUTTON).click();
        return new CheckoutCompletePage(driver);
    }

    public List<BigDecimal> getItemsPrices() {
        return allVisible(PRICE_ITEMS)
                .stream()
                .map(webElement -> webElement.getText().replace("$", "").strip())
                .map(BigDecimal::new)
                .toList();
    }

    public BigDecimal getItemTotalPrice() {
        String itemTotalPriceText =
                visible(ITEM_TOTAL_PRICE)
                        .getText().replace("Item total: $", "").strip();
        return new BigDecimal(itemTotalPriceText);
    }
}
