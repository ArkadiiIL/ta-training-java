package com.epam.training.student_arkadii_ilinov.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PriceUtils {

    private static final Pattern AMOUNT = Pattern.compile("[0-9]+(?:\\.[0-9]{2})?");

    private PriceUtils() {
    }

    public static BigDecimal parseAmount(String raw) {
        Matcher matcher = AMOUNT.matcher(raw);
        if (!matcher.find()) {
            throw new IllegalStateException("Cannot parse a price from: " + raw);
        }
        return new BigDecimal(matcher.group());
    }
}
