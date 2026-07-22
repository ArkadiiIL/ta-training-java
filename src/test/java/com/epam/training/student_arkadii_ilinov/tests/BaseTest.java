package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverFactory;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.extensions.ScreenshotOnFailureExtension;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseTest {
    private final static Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected void initDriver(String browser) {
        BrowserType browserType;
        try {
            browserType = BrowserType.valueOf(browser.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown browser '" + browser + "' in config.properties; supported: "
                            + Arrays.toString(BrowserType.values()), e);
        }
        log.info("Starting test with browser: {}", browserType);
        Allure.parameter("browser", browserType);
        WebDriver webDriver = DriverFactory.createDriver(browserType);
        DriverManager.setDriver(webDriver);
    }

    @AfterEach
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
    }
}
