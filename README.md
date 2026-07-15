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
- Selenium WebDriver
- JUnit 5
- Maven
- Allure Reports

---

## Project Structure

_(To be added)_

---

## How to Run

Run the full test suite (UC-1 and UC-2, Chrome and Firefox in parallel):

```bash
mvn test
```

Run a single scenario (still executes on both browsers in parallel):

```bash
mvn test -Dtest=*CheckoutTest#checkoutSingleItemTest    # UC-1 only
mvn test -Dtest=*CheckoutTest#checkoutMultipleItemsTest # UC-2 only
```

---

## How to View the Report

_(To be added)_
