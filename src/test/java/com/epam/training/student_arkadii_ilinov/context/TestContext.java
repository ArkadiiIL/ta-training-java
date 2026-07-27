package com.epam.training.student_arkadii_ilinov.context;

import com.epam.training.student_arkadii_ilinov.pages.CartPage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutCompletePage;
import com.epam.training.student_arkadii_ilinov.pages.CheckoutOverviewPage;
import com.epam.training.student_arkadii_ilinov.pages.InventoryPage;

public class TestContext {
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }

    public void setInventoryPage(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public void setCartPage(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    public CheckoutOverviewPage getCheckoutOverviewPage() {
        return checkoutOverviewPage;
    }

    public void setCheckoutOverviewPage(CheckoutOverviewPage checkoutOverviewPage) {
        this.checkoutOverviewPage = checkoutOverviewPage;
    }

    public CheckoutCompletePage getCheckoutCompletePage() {
        return checkoutCompletePage;
    }

    public void setCheckoutCompletePage(CheckoutCompletePage checkoutCompletePage) {
        this.checkoutCompletePage = checkoutCompletePage;
    }
}
