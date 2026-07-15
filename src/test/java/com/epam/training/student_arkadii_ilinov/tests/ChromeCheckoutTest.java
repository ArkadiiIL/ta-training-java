package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;

public class ChromeCheckoutTest extends CheckoutTest {
    @Override
    protected BrowserType getBrowserType() {
        return BrowserType.CHROME;
    }
}
