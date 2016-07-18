package io.smartcat.spring.cassandra.showcase.ft.common;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlGenerators {

    public static URI givenAnyUri() {
        try {
            return new URI("http://example.com/some/uri");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
