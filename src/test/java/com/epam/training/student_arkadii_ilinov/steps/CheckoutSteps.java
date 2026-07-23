package com.epam.training.student_arkadii_ilinov.steps;

import com.epam.training.student_arkadii_ilinov.driver.DriverManager;
import com.epam.training.student_arkadii_ilinov.pages.LoginPage;
import io.cucumber.java.en.Given;

public class CheckoutSteps {
    @Given("the login page is open")
    public void openLoginPage() {
        new LoginPage(DriverManager.getDriver()).open();
    }
}
