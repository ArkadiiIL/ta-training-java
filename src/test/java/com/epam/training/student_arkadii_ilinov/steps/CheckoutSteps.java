package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.context.TestContext;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutCompletePage;
import com.epam.training.student_arkadii_ilinov.pages.InventoryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;

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

    @And("the user completes the checkout")
    public void theUserCompletesTheCheckout() {
        InventoryPage inventoryPage = testContext.getInventoryPage();
        CheckoutCompletePage checkoutCompletePage = inventoryPage
                .goToCart()
                .goToCheckout()
                .checkoutYourInformation(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .continueCheckout()
                .finishCheckout();
        testContext.setCheckoutCompletePage(checkoutCompletePage);
    }

    @Then("the order confirmation message {string} is displayed")
    public void theOrderConfirmationMessageIsDisplayed(String expectedMessage) {
        String actualMessage = testContext.getCheckoutCompletePage().getCompleteMessage();

        assertEquals(expectedMessage, actualMessage,
                "The order confirmation message does not match the expected one!");
    }
}
