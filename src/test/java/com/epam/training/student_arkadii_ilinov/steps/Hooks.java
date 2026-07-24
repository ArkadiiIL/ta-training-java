package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverFactory;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Hooks {
    private final static Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        BrowserType browserType = Objects.requireNonNull(
                DriverManager.getBrowser(),
                "Browser is not set — check @Parameters(\"browser\") in the runner and testng.xml");
        log.info("Starting test with browser: {}", browserType);
        DriverManager.setDriver(DriverFactory.createDriver(browserType));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                log.error("Test failed, capturing screenshot");
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "screenshot");
            } else {
                log.warn("Test failed but driver is null, no screenshot");
            }
        }
        log.info("Closing driver");
        DriverManager.quitDriver();
    }
}
