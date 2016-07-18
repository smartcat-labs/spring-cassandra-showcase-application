package io.smartcat.spring.cassandra.showcase.ft.common;

import java.util.UUID;

public class StringGenerators {

    public static String givenAnyString() {
        return "Some-string-123-+_!šđž";
    }

    public static String givenAlphanumericString() {
        return "Somestring123";
    }

    public static String givenUniqueString() {
        return UUID.randomUUID().toString();
    }
}
