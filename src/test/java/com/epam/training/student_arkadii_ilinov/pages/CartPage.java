package com.epam.training.student_arkadii_ilinov.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class CartPage extends BasePage {
    @FindBy(css = "div[data-test='inventory-item-name']")
    private List<WebElement> cartItems;
    @FindBy(css = "button[data-test='checkout']")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isItemPresent(String itemName) {
        return getItemNames().contains(itemName);
    }

    public boolean areItemsPresent(String... itemName) {
        return getItemNames().containsAll(Arrays.asList(itemName));
    }

    public CheckoutPage goToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver);
    }

    private List<String> getItemNames() {
       return wait.until(ExpectedConditions.visibilityOfAllElements(cartItems))
               .stream().map(WebElement::getText).map(String::strip).toList();
    }
}
