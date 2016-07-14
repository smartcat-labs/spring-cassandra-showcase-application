package io.smartcat.spring.cassandra.showcase.ft.error;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RestError {

    private final String message;
    private final int code;

    @JsonCreator
    public RestError(
        @JsonProperty("message") final String message,
        @JsonProperty("code") final int code
    ) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public static RestError fromResponse(final Response response) {
        response.bufferEntity();
        return response.readEntity(RestError.class);
    }
}
