package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverFactory;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.extensions.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseTest {
    private final static Logger log = LoggerFactory.getLogger(BaseTest.class);
    protected abstract BrowserType getBrowserType();

    @BeforeEach
    public void setUp() {
        log.info("Starting test with browser: {}", getBrowserType());
        WebDriver webDriver = DriverFactory.createDriver(getBrowserType());
        DriverManager.setDriver(webDriver);
    }

    @AfterEach
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
    }
}
