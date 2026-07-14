package com.epam.training.student_arkadii_ilinov.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver createDriver(BrowserType browserType) {
        return switch (browserType) {
            case CHROME -> new ChromeDriver();
            case FIREFOX -> new FirefoxDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);
        };
    }
}
