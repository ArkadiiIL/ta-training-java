package com.epam.training.student_arkadii_ilinov.extensions;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        // Проверяем, завершился ли тест с ошибкой
        boolean testFailed = context.getExecutionException().isPresent();

        if (testFailed) {
            try {
                WebDriver driver = DriverManager.getDriver();
                if (driver != null) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment(
                            "Screenshot on failure",
                            "image/png",
                            new ByteArrayInputStream(screenshot),
                            "png"
                    );
                }
            } catch (Exception e) {
                System.err.println("Failed to take screenshot: " + e.getMessage());
            }
        }
    }
}
