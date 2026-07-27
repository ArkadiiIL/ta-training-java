package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.context.TestContext;
import com.epam.training.student_arkadii_ilinov.data.CheckoutUser;
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
    private static final CheckoutUser CHECKOUT_USER = CheckoutUser.defaultUser();
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

    @Then("the item {string} is present in the cart")
    public void theItemIsPresentInTheCart(String itemName) {
        CartPage cartPage = testContext.getCartPage();
        boolean isItemPresent = cartPage.isItemPresent(itemName);
        assertTrue(isItemPresent,
                String.format("Expected item '%s' to be present in the cart, but it was not found.",
                        itemName));
    }

    @And("the user proceeds to the checkout overview")
    public void theUserProceedsToTheCheckoutOverview() {
        CartPage cartPage = testContext.getCartPage();
        CheckoutOverviewPage checkoutOverviewPage = cartPage.
                goToCheckout().
                checkoutYourInformation(CHECKOUT_USER.firstName(), CHECKOUT_USER.lastName(), CHECKOUT_USER.zipCode())
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
