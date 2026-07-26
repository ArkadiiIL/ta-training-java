package com.epam.training.student_arkadii_ilinov.context;

import com.epam.training.student_arkadii_ilinov.pages.CheckoutCompletePage;
import com.epam.training.student_arkadii_ilinov.pages.InventoryPage;

public class TestContext {
    private InventoryPage inventoryPage;
    private CheckoutCompletePage checkoutCompletePage;

    public InventoryPage getInventoryPage() {
        return inventoryPage;
    }

    public void setInventoryPage(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public CheckoutCompletePage getCheckoutCompletePage() {
        return checkoutCompletePage;
    }

    public void setCheckoutCompletePage(CheckoutCompletePage checkoutCompletePage) {
        this.checkoutCompletePage = checkoutCompletePage;
    }
}
