Feature: Checkout

  Background:
    Given the user is logged in

  @uc1
  Scenario Outline: Checkout complete for a single item
    When the user adds "<item>" to the cart
    And the user goes to the cart
    Then the item "<item>" is present in the cart
    And the user proceeds to the checkout overview
    And the user completes the checkout
    Then the order confirmation message "Thank you for your order!" is displayed

    Examples:
      | item                  |
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |

  @uc2
  Scenario Outline: Checkout total equals the sum of prices for two items
    When the user adds "<firstItem>" to the cart
    And the user adds "<secondItem>" to the cart
    And the user goes to the cart
    Then the item "<firstItem>" is present in the cart
    And the item "<secondItem>" is present in the cart
    When the user proceeds to the checkout overview
    Then the total price equals the sum of the item prices
    When the user completes the checkout
    Then the order confirmation message "Thank you for your order!" is displayed

    Examples:
      | firstItem           | secondItem               |
      | Sauce Labs Backpack | Sauce Labs Bike Light    |
      | Sauce Labs Onesie   | Sauce Labs Fleece Jacket |