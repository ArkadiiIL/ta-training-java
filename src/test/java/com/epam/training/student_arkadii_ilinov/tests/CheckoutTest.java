package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.pages.CartPage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutOverviewPage;
import com.epam.training.student_arkadii_ilinov.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class CheckoutTest extends BaseTest {
    private final static String USERNAME = "standard_user";
    private final static String PASSWORD = "secret_sauce";
    private final static String FIRST_NAME = "FirstName";
    private final static String LAST_NAME = "LastName";
    private final static String ZIP = "123-123";
    private final static String COMPLETE_MESSAGE = "Thank you for your order!";

    @DisplayName("UC-1: Checkout with a single item")
    @ParameterizedTest(name = "— {0}")
    @ValueSource(strings = {"Sauce Labs Backpack", "Sauce Labs Onesie"})
    public void checkoutSingleItemTest(String itemName) {
        CartPage cartPage = new LoginPage(DriverManager.getDriver())
                .open()
                .login(USERNAME, PASSWORD)
                .addItemToCart(itemName)
                .goToCart();
        assertTrue(cartPage.isItemPresent(itemName),
                "Item should be present in the cart: " + itemName);
        CheckoutOverviewPage checkoutOverviewPage = cartPage
                .goToCheckout()
                .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP)
                .continueCheckout();
        String completeMessage = checkoutOverviewPage.finishCheckout().getCompleteMessage();
        assertEquals(COMPLETE_MESSAGE, completeMessage);
    }

    @DisplayName("UC-2: Checkout with multiple items")
    @ParameterizedTest(name = "— {0} and {1}")
    @CsvSource({"Sauce Labs Backpack, Sauce Labs Onesie",
                "Sauce Labs Bike Light, Test.allTheThings() T-Shirt (Red)"})
    public void checkoutMultipleItemsTest(String firstItemName, String secondItemName) {
        CartPage cartPage = new LoginPage(DriverManager.getDriver())
                .open()
                .login(USERNAME, PASSWORD)
                .addItemsToCart(firstItemName, secondItemName)
                .goToCart();
        assertTrue(cartPage.areItemsPresent(firstItemName, secondItemName),
                "Items should be present in the cart: " + firstItemName + " and " + secondItemName);
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
