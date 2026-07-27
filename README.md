# SauceDemo Checkout Automation

Automated test framework for the end-to-end checkout flow on [SauceDemo](https://www.saucedemo.com/), built with
Selenium WebDriver, Cucumber on a TestNG runner, and the Page Object Model pattern.

---

## Task Description

### End-to-end flow

**Focus:** User completes full flow from login to checkout **Launch URL:** https://www.saucedemo.com/

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
- Cucumber 7 — Gherkin scenarios, running on a TestNG runner
- TestNG — cross-browser parallel execution
- Maven
- Allure Reports — Gherkin steps, plus screenshot, URL, and page source on failure
- SLF4J + Logback

Locators are CSS selectors throughout, with a single XPath for the add-to-cart button, whose position depends on the
product name.

---

## Prerequisites

- JDK 21
- Maven 3.9+
- Chrome and Firefox installed locally

Drivers are resolved automatically by Selenium Manager, so there's nothing else to set up.

---

## Project Structure

    src/test/java/com/epam/training/student_arkadii_ilinov/
    ├── context/    — TestContext (shared scenario state, injected via picocontainer)
    ├── driver/     — BrowserType, DriverFactory, DriverManager (ThreadLocal, one driver per thread)
    ├── pages/      — Page Objects, one per application page, fluent navigation
    ├── runners/    — CheckoutRunnerTest (AbstractTestNGCucumberTests, sets the browser per thread)
    └── steps/      — LoginSteps, CheckoutSteps (step definitions), Hooks (driver lifecycle + failure context capture)

    src/test/resources/
    ├── features/          — checkout.feature (UC-1, UC-2)
    ├── allure/            — Allure metadata (environment.properties, copied into the results dir at build time)
    ├── config.properties  — base URL, credentials, window size, wait timeout
    ├── testng.xml         — suite: one <test> block per browser, run in parallel
    ├── allure.properties  — results directory
    └── logback.xml        — logging config

---

## Design Patterns

- **BDD (Cucumber/Gherkin)** — scenarios read as Given/When/Then specifications; step definitions map them onto page
  actions, so the feature files stay free of browser and selector detail.
- **Page Object Model** — each page is a class, so steps read in terms of actions rather than selectors.
- **Factory** — `DriverFactory` builds a configured `WebDriver` for the requested `BrowserType`.
- **Fluent Interface** — page methods return the next page, so a scenario reads as a chain of steps.
- **Dependency Injection** — picocontainer injects a shared `TestContext` into the step classes; a fresh graph per
  scenario keeps it thread-safe under the parallel run.
- **ThreadLocal driver storage** — `DriverManager` keeps one driver per thread, which is what makes the parallel
  Chrome/Firefox run safe.

---

## Logging

SLF4J with Logback. The driver lifecycle and each scenario's start and finish are logged, with the thread name in the
pattern so the parallel scenarios don't tangle in the output.

---

## How to Run

Run the full test suite (UC-1 and UC-2, across both browsers, in parallel):

```bash
mvn test
```

Maven runs the suite defined in `testng.xml`, which has one `<test>` block per browser. To run just one browser, comment
out the other block.

Scenarios are tagged, so a subset can be run by tag:

```bash
# UC-1 only
mvn test "-Dcucumber.filter.tags=@uc1"
```

```bash
# UC-2 only
mvn test "-Dcucumber.filter.tags=@uc2"
```

Each tagged scenario still executes across both browsers in parallel.

---

## How to View the Report

Test results are written to `target/allure-results` during the run, so run `mvn test` first. To generate and open the
HTML report:

```bash
mvn allure:serve
```

The report opens in a browser automatically. For a persistent report instead of a temporary one:

```bash
mvn allure:report
```

It will be generated to `target/site/allure-maven-plugin/index.html`.

The Behaviors tab groups the scenarios as an Epic → Feature → Scenario tree, each scenario showing its Given / When /
Then steps and tagged with a severity. The Overview page includes an Environment block (browsers, base URL, Selenium and
Java versions), populated from `environment.properties`. Failed scenarios have the screenshot, the URL, and the page
source attached.