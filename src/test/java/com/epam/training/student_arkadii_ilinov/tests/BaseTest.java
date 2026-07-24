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
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;

public abstract class BaseTest implements IHookable {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    private static final String SCREENSHOT_NAME = "Screenshot on failure";

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        BrowserType browserType = BrowserType.valueOf(browser);
        DriverManager.setDriver(DriverFactory.createDriver(browserType));
        DriverManager.setBrowser(browserType);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("Closing driver");
        DriverManager.quitDriver();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        String testName = testResult.getName();
        log.info("Starting test: {}", testName);

        callBack.runTestMethod(testResult);

        Throwable failure = testResult.getThrowable();
        if (failure == null) {
            log.info("Test passed: {}", testName);
            return;
        }

        log.error("Test failed: {}", testName, failure);
        attachScreenshot();
    }

    private void attachScreenshot() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("Driver is null, screenshot skipped");
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(SCREENSHOT_NAME, "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            log.warn("Failed to capture screenshot", e);
        }
    }
}
