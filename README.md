# SauceDemo Checkout Automation

Automated test framework for the end-to-end checkout flow on [SauceDemo](https://www.saucedemo.com/), built with Selenium WebDriver, JUnit 5, and the Page Object Model pattern.

---

## Task Description

### End-to-end flow

**Focus:** User completes full flow from login to checkout
**Launch URL:** https://www.saucedemo.com/

### UC-1 Checkout Flow (one item)

- Login with standard_user.
- Add a specific product to the cart (parametrize product name, e.g., "Sauce Labs Backpack").
- Go to Cart and validate the item is present.
- Proceed to Checkout.
- Fill in Information form (First Name, Last Name, Zip).
- Complete checkout and validate success message: "Thank you for your order!"

### UC-2 Checkout Flow (several items)

- Login with standard_user.
- Add a specific product to the cart (parametrize product name, e.g., "Sauce Labs Backpack").
- Add another product to the cart.
- Go to Cart and validate both items are present.
- Proceed to Checkout.
- Fill in Information form (First Name, Last Name, Zip).
- Validate final price equals the sum of both product prices.
- Complete checkout and validate success message: "Thank you for your order!"

### Technical Requirements

- **Tool:** Selenium WebDriver
- **Browsers:** Chrome, Firefox (Run in Parallel)
- **Pattern:** Page Object Model (POM)
- **Locators:** CSS Selectors, XPath
- **Reporting:** Allure (or similar HTML report)
- **Documentation:** README.md with execution and report instructions

---

## Tech Stack

- Java 21
- Selenium WebDriver 4.45
- JUnit 5 — parameterized cross-browser execution & parallel test execution
- Maven
- Allure Reports — steps, parameters, and screenshots on failure
- SLF4J + Logback

Locators are CSS selectors throughout, with a single XPath for the add-to-cart button, whose position depends on the product name.

---

## Prerequisites

- JDK 21
- Maven 3.9+
- Chrome and Firefox installed locally

Drivers are resolved automatically by Selenium Manager, so there's nothing else to set up.

---

## Project Structure

    src/test/java/com/epam/training/student_arkadii_ilinov/
    ├── driver/       — BrowserType, DriverFactory, DriverManager (ThreadLocal, one driver per thread)
    ├── pages/        — Page Objects, one per application page, fluent navigation
    ├── tests/        — BaseTest (driver lifecycle), CheckoutTest (UC-1, UC-2)
    ├── extensions/   — ScreenshotOnFailureExtension
    └── utils/        — ConfigReader

    src/test/resources/
    ├── config.properties          — base URL, test credentials, supported browsers list
    ├── junit-platform.properties  — parallel execution settings
    ├── allure.properties          — results directory
    └── logback.xml                — logging config

---

## Design Patterns

- **Page Object Model** — each page is a class, so tests read in terms of actions rather than selectors.
- **Factory** — `DriverFactory` builds a configured `WebDriver` for the requested `BrowserType`.
- **Fluent Interface** — page methods return the next page, so a scenario reads as a chain of steps.
- **ThreadLocal driver storage** — `DriverManager` keeps one driver per thread, which is what makes the parallel Chrome/Firefox run safe.
- **Data-Driven Matrix (Stream/flatMap)** — dynamically combines available browsers with test datasets to generate cross-browser test cases automatically.

---

## Logging

SLF4J with Logback. The driver lifecycle and each test's start and finish are logged, with the thread name in the pattern so the two parallel browsers don't tangle in the output.

---

## How to Run

Run the full test suite (UC-1 and UC-2, Chrome and Firefox in parallel):

```bash
mvn test
```

Run a single scenario (still executes on both browsers in parallel):

#UC-1 only
```bash
mvn test -Dtest=CheckoutTest#checkoutSingleItemTest*
```
#UC-2 only
```bash
mvn test -Dtest=CheckoutTest#checkoutMultipleItemsTest*
```

---

## How to View the Report

Test results are written to `target/allure-results` during the run. To generate and open the HTML report:

```bash
mvn allure:serve
```

The report opens in a browser automatically. For a persistent report instead of a temporary one:

```bash
mvn allure:report
```

It will be generated to `target/site/allure-maven-plugin/index.html`.

Each test is structured as Given / When / Then steps, and the Behaviors tab groups the scenarios as an Epic → Feature → Story tree. Every test carries its browser and, for UC-2, the item prices as parameters; failed tests have a screenshot attached.