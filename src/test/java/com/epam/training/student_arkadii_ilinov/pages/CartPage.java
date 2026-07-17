package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class CartPage extends BasePage {
    private final static By CART_ITEMS = By.cssSelector("div[data-test='inventory-item-name']");
    private final static By CHECKOUT_BUTTON = By.cssSelector("button[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isItemPresent(String itemName) {
        return getItemNames().contains(itemName);
    }

    public boolean areItemsPresent(String... itemNames) {
        return getItemNames().containsAll(Arrays.asList(itemNames));
    }

    @Step("Proceed to checkout")
    public CheckoutPage goToCheckout() {
        clickable(CHECKOUT_BUTTON).click();
        return new CheckoutPage(driver);
    }

    private List<String> getItemNames() {
        return allVisible(CART_ITEMS)
                .stream().map(WebElement::getText).map(String::strip).toList();
    }
}
