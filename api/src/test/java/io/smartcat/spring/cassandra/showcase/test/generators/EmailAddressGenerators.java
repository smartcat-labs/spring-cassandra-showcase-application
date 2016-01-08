package io.smartcat.spring.cassandra.showcase.test.generators;

import java.util.Random;

import io.smartcat.spring.cassandra.showcase.domain.account.EmailAddress;

public class EmailAddressGenerators {

    private static final String[] VALID_ADDRESSES = {
        "johnatan@example.com",
        "alice@museum.com",
        "bob+something@gmail.com",
        "jane@foo-bar.net"
    };

    private static final String[] INVALID_ADDRESSES = {
        "@",
        ".",
        "jane@",
        "@.com",
        "@com",
        "joe@localhost"
    };

    public static EmailAddress givenAnyEmailAddress() {
        return new EmailAddress("joe@example.com");
    }

    public static String givenAnEmailString() {
        return givenAnyEmailString();
    }

    public static String givenAnyEmailString() {
        return "joe@example.com";
    }

    public static String[] givenInvalidEmailAddresses() {
        return INVALID_ADDRESSES;
    }

    public static String[] givenValidEmailAddresses() {
        return VALID_ADDRESSES;
    }

    public static EmailAddress givenUniqueEmailAddress() {
        return new EmailAddress(String.format("someone%d@example.com", new Random().nextInt()));
    }
}
