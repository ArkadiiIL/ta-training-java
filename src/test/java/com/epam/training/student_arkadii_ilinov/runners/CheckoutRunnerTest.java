package com.epam.training.student_arkadii_ilinov.runners;

import com.epam.training.student_arkadii_ilinov.driver.BrowserType;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.Arrays;

@CucumberOptions(
        features = "classpath:features",
        glue = "com.epam.training.student_arkadii_ilinov.steps",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class CheckoutRunnerTest extends AbstractTestNGCucumberTests {
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        BrowserType browserType;
        try {
            browserType = BrowserType.valueOf(browser.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown browser '" + browser + "' in config.properties; supported: "
                            + Arrays.toString(BrowserType.values()), e);
        }
        DriverManager.setBrowser(browserType);
    }
}
