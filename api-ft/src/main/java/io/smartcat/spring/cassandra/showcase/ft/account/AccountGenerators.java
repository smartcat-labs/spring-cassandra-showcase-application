package io.smartcat.spring.cassandra.showcase.ft.account;

import java.util.Random;

import io.smartcat.spring.cassandra.showcase.ft.common.UrlGenerators;

public class AccountGenerators {

    public static String givenAnyLastName() {
        return "Doe";
    }

    public static String givenAnyFirstName() {
        return "Johnatan";
    }

    public static String givenAnyPassword() {
        return "mypassword123+*";
    }

    public static String givenAnyEmail() {
        return givenUniqueEmail();
    }

    public static String givenUniqueEmail() {
        return String.format("someone%d@example.com", new Random().nextInt());
    }

    public static String givenProfileImageUrl() {
        return UrlGenerators.givenAnyUri().toString();
    }
}