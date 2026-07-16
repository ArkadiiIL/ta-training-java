package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.pages.CartPage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutOverviewPage;
import com.epam.training.student_arkadii_ilinov.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class CheckoutTest extends BaseTest {
    private final static String USERNAME = "standard_user";
    private final static String PASSWORD = "secret_sauce";
    private final static String FIRST_ITEM_NAME = "Sauce Labs Backpack";
    private final static String SECOND_ITEM_NAME = "Sauce Labs Fleece Jacket";
    private final static String FIRST_NAME = "FirstName";
    private final static String LAST_NAME = "LastName";
    private final static String ZIP = "123-123";
    private final static String COMPLETE_MESSAGE = "Thank you for your order!";

    @Test
    @DisplayName("UC-1: Checkout with a single item")
    public void checkoutSingleItemTest() {
        CartPage cartPage = new LoginPage(DriverManager.getDriver())
                .open()
                .login(USERNAME, PASSWORD)
                .addItemToCart(FIRST_ITEM_NAME)
                .goToCart();
        assertTrue(cartPage.isItemPresent(FIRST_ITEM_NAME),
                "Item should be present in the cart: " + FIRST_ITEM_NAME);
        CheckoutOverviewPage checkoutOverviewPage = cartPage
                .goToCheckout()
                .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP)
                .continueCheckout();
        String completeMessage = checkoutOverviewPage.finishCheckout().getCompleteMessage();
        assertEquals(COMPLETE_MESSAGE, completeMessage);
    }

    @Test
    @DisplayName("UC-2: Checkout with multiple items")
    public void checkoutMultipleItemsTest() {
        CartPage cartPage = new LoginPage(DriverManager.getDriver())
                .open()
                .login(USERNAME, PASSWORD)
                .addItemsToCart(FIRST_ITEM_NAME, SECOND_ITEM_NAME)
                .goToCart();
        assertTrue(cartPage.areItemsPresent(FIRST_ITEM_NAME, SECOND_ITEM_NAME),
                "Items should be present in the cart: " + FIRST_ITEM_NAME + " and " + SECOND_ITEM_NAME);
        CheckoutOverviewPage checkoutOverviewPage = cartPage
                .goToCheckout()
                .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP)
                .continueCheckout();
        List<BigDecimal> prices = checkoutOverviewPage.getItemsPrices();
        BigDecimal totalPrice = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(0, totalPrice.compareTo(checkoutOverviewPage.getItemTotalPrice()),
                "Item total should equal the sum of individual item prices");
        String completeMessage = checkoutOverviewPage.finishCheckout().getCompleteMessage();
        assertEquals(COMPLETE_MESSAGE, completeMessage);
    }
}
