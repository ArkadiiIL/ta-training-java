package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;

public class FirefoxCheckoutTest extends CheckoutTest {
    @Override
    protected BrowserType getBrowserType() {
        return BrowserType.FIREFOX;
    }
}
