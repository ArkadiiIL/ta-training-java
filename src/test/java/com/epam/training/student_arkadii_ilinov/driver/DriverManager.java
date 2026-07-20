package com.epam.training.student_arkadii_ilinov.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<BrowserType> browser = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
        }
        driver.remove();
    }
    public static void setBrowser(BrowserType b) { browser.set(b); }
    public static BrowserType getBrowser() { return browser.get(); }
}
