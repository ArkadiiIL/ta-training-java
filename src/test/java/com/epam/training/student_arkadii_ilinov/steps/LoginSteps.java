package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.context.TestContext;
import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.pages.InventoryPage;
import com.epam.training.student_arkadii_ilinov.pages.LoginPage;
import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;
import io.cucumber.java.en.Given;

public class LoginSteps {
    private final TestContext testContext;

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    private final static String USERNAME = ConfigReader.getUsername();
    private final static String PASSWORD = ConfigReader.getPassword();

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
       InventoryPage inventoryPage = new LoginPage(DriverManager.getDriver()).open().login(USERNAME, PASSWORD);
       testContext.setInventoryPage(inventoryPage);
    }
}
