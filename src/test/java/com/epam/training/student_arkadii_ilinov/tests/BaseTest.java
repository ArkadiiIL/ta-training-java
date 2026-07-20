package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverFactory;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.extensions.ScreenshotOnFailureExtension;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

@Listeners(ScreenshotOnFailureExtension.class)
public abstract class BaseTest {
    private final static Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        BrowserType browserType = BrowserType.valueOf(browser);
        log.info("Starting test with browser: {}", browserType);
        Allure.parameter("browser", browserType);
        WebDriver webDriver = DriverFactory.createDriver(browserType);
        DriverManager.setDriver(webDriver);
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
    }
}
