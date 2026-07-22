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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("SauceDemo E2E")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {
    private final static String USERNAME = ConfigReader.getUsername();
    private final static String PASSWORD = ConfigReader.getPassword();
    private final static String FIRST_NAME = "FirstName";
    private final static String LAST_NAME = "LastName";
    private final static String ZIP = "123-123";
    private final static String COMPLETE_MESSAGE = "Thank you for your order!";

    static Stream<Arguments> singleItemCases() {
        List<String> items = List.of("Sauce Labs Backpack", "Sauce Labs Onesie");
        return ConfigReader.getBrowsers().stream()
                .flatMap(browser -> items.stream().map(item -> Arguments.of(browser, item)));
    }

    static Stream<Arguments> multipleItemCases() {
        List<String[]> pairs = List.of(
                new String[]{"Sauce Labs Backpack", "Sauce Labs Onesie"},
                new String[]{"Sauce Labs Bike Light", "Test.allTheThings() T-Shirt (Red)"}
        );
        return ConfigReader.getBrowsers().stream()
                .flatMap(browser -> pairs.stream()
                        .map(p -> Arguments.of(browser, p[0], p[1])));
    }

    @DisplayName("UC-1: Checkout with a single item")
    @ParameterizedTest(name = "[{0}] — {1}")
    @MethodSource("singleItemCases")
    public void checkoutSingleItemTest(String browser, String itemName) {
        initDriver(browser);
        Allure.story("Checkout with a single item");
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
                assertEquals(COMPLETE_MESSAGE, completePage.getCompleteMessage()));
    }

    @DisplayName("UC-2: Checkout with multiple items")
    @ParameterizedTest(name = "[{0}] — {1} and {2}")
    @MethodSource("multipleItemCases")
    public void checkoutMultipleItemsTest(String browser, String firstItemName, String secondItemName) {
        initDriver(browser);
        Allure.story("Checkout with multiple items");
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
                    assertEquals(0, totalPrice.compareTo(checkoutOverviewPage.getItemTotalPrice()),
                            "Item total should equal the sum of individual item prices");
                });

        Allure.step("And the order is confirmed",
                () -> {
                    String completeMessage = checkoutOverviewPage.finishCheckout().getCompleteMessage();
                    assertEquals(COMPLETE_MESSAGE, completeMessage);
                });
    }
}
