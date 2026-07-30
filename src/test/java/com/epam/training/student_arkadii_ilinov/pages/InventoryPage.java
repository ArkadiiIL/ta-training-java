package com.epam.training.student_arkadii_ilinov.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.epam.training.student_arkadii_ilinov.utils.XPathUtils.xpathLiteral;

public class InventoryPage extends BasePage {
    private final static String ADD_BUTTON_XPATH = """
            //div[@data-test='inventory-item-name' and text()=%s]\
            /ancestor::div[@data-test='inventory-item']\
            //button[starts-with(@data-test, 'add-to-cart')]""";

    private final static By CART_LINK = By.cssSelector("a[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage addItemToCart(String itemName) {
        clickable(addButtonLocator(itemName)).click();
        return this;
    }

    private By addButtonLocator(String itemName) {
        return By.xpath(String.format(ADD_BUTTON_XPATH, xpathLiteral(itemName)));
    }

    public CartPage goToCart() {
        clickable(CART_LINK).click();
        return new CartPage(driver);
    }
}
