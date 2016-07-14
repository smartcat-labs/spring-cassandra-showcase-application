package io.smartcat.spring.cassandra.showcase.adapter.http.account.dto;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.smartcat.spring.cassandra.showcase.domain.account.Account;
import io.smartcat.spring.cassandra.showcase.domain.account.AccountRole;

public class AccountDto {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final Set<AccountRole> roles;
    private final Instant registeredAt;
    private final String profileImageUrl;

    @JsonCreator
    public AccountDto(
        @JsonProperty("email") final String email,
        @JsonProperty("firstName") final String firstName,
        @JsonProperty("lastName") final String lastName,
        @JsonProperty("roles") final Set<AccountRole> roles,
        @JsonProperty("registeredAt") final Instant registeredAt,
        @JsonProperty("profileImageUrl") final String profileImageUrl
    ) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
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

    public Set<AccountRole> getRoles() {
        return roles;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static AccountDto fromAccount(final Account account) {
        return new AccountDto(account.email().address(), account.firstName(), account.lastName(),
                account.roles(), account.registeredAt(), account.profileImageUrl().toString());
    }
}
