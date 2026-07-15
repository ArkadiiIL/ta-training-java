package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FirefoxCheckoutTest extends CheckoutTest {
    @Override
    protected BrowserType getBrowserType() {
        return BrowserType.FIREFOX;
    }
}
