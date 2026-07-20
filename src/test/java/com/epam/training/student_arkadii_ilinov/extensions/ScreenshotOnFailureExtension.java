package com.epam.training.student_arkadii_ilinov.extensions;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class ScreenshotOnFailureExtension implements ITestListener {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotOnFailureExtension.class);

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("Cannot take screenshot: driver is null");
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                    "Screenshot on failure",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
            );
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
        }
    }
}
