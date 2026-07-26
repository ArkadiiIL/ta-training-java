package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.context.TestContext;
import com.epam.training.student_arkadii_ilinov.pages.CartPage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutCompletePage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutOverviewPage;
import com.epam.training.student_arkadii_ilinov.pages.InventoryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutSteps {
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String ZIP_CODE = "12345";
    private final TestContext testContext;

    public CheckoutSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("the user adds {string} to the cart")
    public void theUserAddsItemToTheCart(String itemName) {
        InventoryPage inventoryPage = testContext.getInventoryPage();
        inventoryPage.addItemToCart(itemName);
    }

    @And("the user goes to the cart")
    public void theUserGoesToTheCart() {
        InventoryPage inventoryPage = testContext.getInventoryPage();
        CartPage cartPage = inventoryPage.goToCart();
        testContext.setCartPage(cartPage);
    }

    @Then("the cart contains {string} and {string}")
    public void theCartContainsFirstItemAndSecondItem(String firstItemName, String secondItemName) {
        CartPage cartPage = testContext.getCartPage();
        boolean areItemPresent = cartPage.areItemsPresent(firstItemName, secondItemName);
        assertTrue(areItemPresent,
                String.format("Expected items '%s' and '%s' to be present in the cart, but they were not found.",
                        firstItemName, secondItemName));
    }

    @And("the user proceeds to the checkout overview")
    public void theUserProceedsToTheCheckoutOverview() {
        CartPage cartPage = testContext.getCartPage();
        CheckoutOverviewPage checkoutOverviewPage = cartPage.
                goToCheckout().
                checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .continueCheckout();
        testContext.setCheckoutOverviewPage(checkoutOverviewPage);
    }

    @Then("the total price equals the sum of the item prices")
    public void theTotalPriceEqualsTheSumOfTheItemPrices() {
        CheckoutOverviewPage checkoutOverviewPage = testContext.getCheckoutOverviewPage();

        BigDecimal expectedTotalPrice = checkoutOverviewPage.getItemTotalPrice()
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal actualTotalPrice = checkoutOverviewPage.getItemsPrices().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(actualTotalPrice, expectedTotalPrice,
                "The sum of item prices does not match the total shown on the overview page.");
    }


    @And("the user completes the checkout")
    public void theUserCompletesTheCheckout() {
        CheckoutOverviewPage checkoutOverviewPage = testContext.getCheckoutOverviewPage();
        CheckoutCompletePage checkoutCompletePage = checkoutOverviewPage.finishCheckout();
        testContext.setCheckoutCompletePage(checkoutCompletePage);
    }

    @Then("the order confirmation message {string} is displayed")
    public void theOrderConfirmationMessageIsDisplayed(String expectedMessage) {
        String actualMessage = testContext.getCheckoutCompletePage().getCompleteMessage();

        assertEquals(actualMessage, expectedMessage,
                "The order confirmation message does not match the expected one!");
    }
}
