package com.epam.training.student_arkadii_ilinov.driver;

import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final String WINDOW_WIDTH = ConfigReader.getWindowWidth();
    private static final String WINDOW_HEIGHT = ConfigReader.getWindowHeight();
    private DriverFactory() {
    }

    public static WebDriver createDriver(BrowserType browserType) {
        log.info("Creating driver for browser type {}", browserType);
        return switch (browserType) {
            case CHROME -> createChromeDriver();
            case FIREFOX -> createFirefoxDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=" + WINDOW_WIDTH + "," + WINDOW_HEIGHT);
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("signon.rememberSignons", false);
        options.addArguments("--width=" + WINDOW_WIDTH, "--height=" + WINDOW_HEIGHT);
        return new FirefoxDriver(options);
    }
}
