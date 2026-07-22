package com.epam.training.student_arkadii_ilinov.tests;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverFactory;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;

public abstract class BaseTest {
    private final static Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        BrowserType browserType = BrowserType.valueOf(browser);
        log.info("Starting test with browser: {}", browserType);
        WebDriver webDriver = DriverFactory.createDriver(browserType);
        DriverManager.setDriver(webDriver);
        DriverManager.setBrowser(browserType);
    }

    @AfterMethod
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
    }

    protected void withScreenshotOnFailure(Runnable scenario) {
        try {
            scenario.run();
        } catch (Throwable t) {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                log.error("Test failed, capturing screenshot", t);
                Allure.addAttachment("Screenshot on failure", "image/png",
                        new ByteArrayInputStream(
                                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)), "png");
            } else {
                log.warn("Test failed but driver is null, no screenshot");
            }
            throw t;
        }
    }
}
