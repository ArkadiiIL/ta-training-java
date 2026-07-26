Feature: Checkout

  Background:
    Given the user is logged in

  Scenario: Checkout complete for a single item
    When the user adds "Sauce Labs Backpack" to the cart
    And the user completes the checkout
    Then the order confirmation message "Thank you for your order!" is displayed