package com.epam.training.student_arkadii_ilinov.data;

import com.epam.training.student_arkadii_ilinov.utils.ConfigReader;

public record CheckoutUser(String firstName, String lastName, String zipCode) {
    public static CheckoutUser defaultUser() {
        return new CheckoutUser(
                ConfigReader.getFirstName(),
                ConfigReader.getLastName(),
                ConfigReader.getZipCode());
    }
}
