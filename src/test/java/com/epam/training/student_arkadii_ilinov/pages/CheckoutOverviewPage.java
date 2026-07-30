package com.epam.training.student_arkadii_ilinov.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.training.student_arkadii_ilinov.utils.PriceUtils.parseAmount;

public class CheckoutOverviewPage extends BasePage {
    private final static By FINISH_BUTTON = By.cssSelector("button[data-test='finish']");
    private final static By PRICE_ITEMS = By.cssSelector("div[data-test='inventory-item-price']");
    private final static By ITEM_TOTAL_PRICE = By.cssSelector("div[data-test='subtotal-label']");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutCompletePage finishCheckout() {
        clickable(FINISH_BUTTON).click();
        return new CheckoutCompletePage(driver);
    }

    public List<BigDecimal> getItemsPrices() {
        return allVisible(PRICE_ITEMS)
                .stream()
                .map(webElement -> parseAmount(webElement.getText()))
                .toList();
    }

    public BigDecimal getItemTotalPrice() {
        return parseAmount(visible(ITEM_TOTAL_PRICE).getText());
    }
}
