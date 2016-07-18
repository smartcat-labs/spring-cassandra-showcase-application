package io.smartcat.spring.cassandra.showcase.adapter.http.account.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCreateRequest {

    @NotNull(message = "Email address is required.")
    private final String email;

    @NotNull(message = "Password is required.")
    private final String password;

    @NotNull(message = "First name is required.")
    private final String firstName;

    @NotNull(message = "Last name is required.")
    private final String lastName;

    @JsonProperty
    private final String profileImageUrl;

    @JsonCreator
    public AccountCreateRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("profileImageUrl") String profileImageUrl
    ) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImageUrl = profileImageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String profileImageUrl() {
        return profileImageUrl;
    }

}
