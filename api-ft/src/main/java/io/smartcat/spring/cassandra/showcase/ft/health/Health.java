package io.smartcat.spring.cassandra.showcase.ft.health;

import javax.ws.rs.client.Invocation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {

    private String status;

    @JsonCreator
    public Health(@JsonProperty("status") String status) {
        this.status = status;
    }

    public boolean isUp() {
        return status.equals("UP");
    }

    public static Health from(Invocation request) {
        return request.invoke().readEntity(Health.class);
    }
}
