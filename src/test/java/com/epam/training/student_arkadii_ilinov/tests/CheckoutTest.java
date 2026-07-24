package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.pages.CartPage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutCompletePage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutOverviewPage;
import com.epam.training.student_arkadii_ilinov.pages.LoginPage;
import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("SauceDemo E2E")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {
    private final static String USERNAME = ConfigReader.getUsername();
    private final static String PASSWORD = ConfigReader.getPassword();
    private final static String FIRST_NAME = "FirstName";
    private final static String LAST_NAME = "LastName";
    private final static String ZIP = "123-123";
    private final static String COMPLETE_MESSAGE = "Thank you for your order!";

    @DataProvider(name = "singleItems")
    public Object[][] singleItems() {
        return new Object[][]{
                {"Sauce Labs Backpack"},
                {"Sauce Labs Onesie"}
        };
    }

    @DataProvider(name = "itemPairs")
    public Object[][] itemPairs() {
        return new Object[][]{
                {"Sauce Labs Backpack", "Sauce Labs Onesie"},
                {"Sauce Labs Bike Light", "Test.allTheThings() T-Shirt (Red)"}
        };
    }

    @Test(
            description = "UC-1: Checkout with a single item",
            dataProvider = "singleItems")
    @Story("Checkout with a single item")
    public void checkoutSingleItemTest(String itemName) {
            Allure.parameter("browser", DriverManager.getBrowser());
            CartPage cartPage = Allure.step("Given the cart contains " + itemName,
                    () -> {
                        CartPage cart = new LoginPage(DriverManager.getDriver())
                                .open()
                                .login(USERNAME, PASSWORD)
                                .addItemToCart(itemName)
                                .goToCart();
                        assertTrue(cart.isItemPresent(itemName),
                                "Item should be present in the cart: " + itemName);
                        return cart;
                    });

            CheckoutCompletePage completePage = Allure.step("When the order is placed", () ->
                    cartPage.goToCheckout()
                            .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP)
                            .continueCheckout()
                            .finishCheckout());

            Allure.step("Then the order is confirmed", () ->
                    assertEquals(completePage.getCompleteMessage(), COMPLETE_MESSAGE));
    }

    @Test(
            description = "UC-2: Checkout with multiple items",
            dataProvider = "itemPairs"
    )
    @Story("Checkout with multiple items")
    public void checkoutMultipleItemsTest(String firstItemName, String secondItemName) {
            Allure.parameter("browser", DriverManager.getBrowser());
            CartPage cartPage = Allure.step(
                    "Given the cart contains " + firstItemName + " and " + secondItemName,
                    () -> {
                        CartPage cart = new LoginPage(DriverManager.getDriver())
                                .open()
                                .login(USERNAME, PASSWORD)
                                .addItemsToCart(firstItemName, secondItemName)
                                .goToCart();
                        assertTrue(cart.areItemsPresent(firstItemName, secondItemName),
                                "Items should be present in the cart: " + firstItemName + " and " + secondItemName);
                        return cart;
                    });

            CheckoutOverviewPage checkoutOverviewPage = Allure.step("When the user proceeds to checkout",
                    () -> cartPage
                            .goToCheckout()
                            .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP)
                            .continueCheckout());

            Allure.step("Then the item total equals the sum of item prices",
                    () -> {
                        List<BigDecimal> prices = checkoutOverviewPage.getItemsPrices();
                        BigDecimal totalPrice = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                        assertEquals(totalPrice.compareTo(checkoutOverviewPage.getItemTotalPrice()), 0,
                                "Item total should equal the sum of individual item prices");
                    });

            Allure.step("And the order is confirmed",
                    () -> {
                        String completeMessage = checkoutOverviewPage.finishCheckout().getCompleteMessage();
                        assertEquals(completeMessage, COMPLETE_MESSAGE);
                    });
    }
}
