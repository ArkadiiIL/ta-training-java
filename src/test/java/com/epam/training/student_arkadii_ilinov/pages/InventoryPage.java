package com.epam.training.student_arkadii_ilinov.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {
    private final static String ADD_BUTTON_XPATH = """
        //div[text()='%s']
        /ancestor::div[@data-test='inventory-item']
        //button[starts-with(@data-test, 'add-to-cart')]
        """;
    @FindBy(css = "a[data-test='shopping-cart-link']")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Add item {itemName} to cart")
    public InventoryPage addItemToCart(String itemName) {
        String addButtonXpath = String.format(ADD_BUTTON_XPATH, itemName);
        By addButtonLocator = By.xpath(addButtonXpath);
        wait.until(ExpectedConditions.elementToBeClickable(addButtonLocator)).click();
        return this;
    }

    @Step("Add items to cart")
    public InventoryPage addItemsToCart(String... itemNames) {
        for (String itemName : itemNames) {
            addItemToCart(itemName);
        }
        return this;
    }

    @Step("Go to cart")
    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }
}
