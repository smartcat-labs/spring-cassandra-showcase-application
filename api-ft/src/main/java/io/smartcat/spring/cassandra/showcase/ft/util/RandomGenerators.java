package io.smartcat.spring.cassandra.showcase.ft.util;

import java.util.Random;

public class RandomGenerators {

    public static final Random RANDOM = new Random();
    private static final char[] ALPHANUMERIC_SYMBOLS;

    static {
        // Initialize alphanumeric character array.
        final StringBuilder sb = new StringBuilder(62);
        for (char ch = '0'; ch <= '9'; ch++) {
            sb.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            sb.append(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            sb.append(ch);
        }
        ALPHANUMERIC_SYMBOLS = sb.toString().toCharArray();
    }

    public static int givenRandomIntegerInInterval(final int min, final int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String givenRandomAlphaNumbericString(final int min, final int max) {
        // Fast implementation of random alphanumeric string generator.
        final int stringLength = (min == max) ? max : RANDOM.nextInt(max - min) + min;
        final StringBuilder sb = new StringBuilder(stringLength);
        for (int length = 0; length < stringLength; length++) {
            sb.append(ALPHANUMERIC_SYMBOLS[RANDOM.nextInt(ALPHANUMERIC_SYMBOLS.length)]);
        }
        return sb.toString();
    }
}
