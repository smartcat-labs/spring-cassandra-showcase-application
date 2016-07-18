package io.smartcat.spring.cassandra.showcase.ft.account;

import java.time.Instant;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final Instant registeredAt;
    private final String profileImageUrl;

    @JsonCreator
    public Account(
        @JsonProperty("email") final String email,
        @JsonProperty("firstName") final String firstName,
        @JsonProperty("lastName") final String lastName,
        @JsonProperty("registeredAt") final Instant registeredAt,
            @JsonProperty("profileImageUrl") final String profileImageUrl
    ) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredAt = registeredAt;
        this.profileImageUrl = profileImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public String getProfileImage() {
        return profileImageUrl;
    }

    public static Account fromResponse(final Response response) {
        response.bufferEntity();
        return response.readEntity(Account.class);
    }
}
