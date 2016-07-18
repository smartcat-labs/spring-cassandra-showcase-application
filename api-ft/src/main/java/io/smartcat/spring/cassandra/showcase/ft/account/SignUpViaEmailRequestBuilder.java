package io.smartcat.spring.cassandra.showcase.ft.account;

import static io.smartcat.spring.cassandra.showcase.ft.account.AccountGenerators.givenAnyFirstName;
import static io.smartcat.spring.cassandra.showcase.ft.account.AccountGenerators.givenAnyLastName;
import static io.smartcat.spring.cassandra.showcase.ft.account.AccountGenerators.givenAnyPassword;
import static io.smartcat.spring.cassandra.showcase.ft.account.AccountGenerators.givenUniqueEmail;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import io.smartcat.spring.cassandra.showcase.ft.common.RequestBuilder;

public class SignUpViaEmailRequestBuilder extends RequestBuilder<SignUpViaEmailRequestBuilder> {

    public SignUpViaEmailRequestBuilder() {
        super();
        withHttpMethod(HttpMethod.POST);
        withEndPoint("account");
        withContentType(MediaType.APPLICATION_JSON);

        withFirstName(givenAnyFirstName());
        withLastName(givenAnyLastName());
        withEmail(givenUniqueEmail());
        withPassword(givenAnyPassword());
        withProfileImageUrl(AccountGenerators.givenProfileImageUrl());
    }

    public SignUpViaEmailRequestBuilder(final String email) {
        this();
        withEmail(email);
    }

    public SignUpViaEmailRequestBuilder withEmail(final String email) {
        withBodyParameter(BodyParameters.EMAIL, email);
        return this;
    }

    public SignUpViaEmailRequestBuilder withFirstName(final String firstName) {
        withBodyParameter(BodyParameters.FIRST_NAME, firstName);
        return this;
    }

    public SignUpViaEmailRequestBuilder withLastName(final String lastName) {
        withBodyParameter(BodyParameters.LAST_NAME, lastName);
        return this;
    }

    public SignUpViaEmailRequestBuilder withPassword(final String password) {
        withBodyParameter(BodyParameters.PASSWORD, password);
        return this;
    }

    public SignUpViaEmailRequestBuilder withProfileImageUrl(final String profileImageUrl) {
        withBodyParameter(BodyParameters.PROFILE_IMAGE_URL, profileImageUrl);
        return this;
    }

    public static Invocation aSignUpViaEmailRequest(final String emailAddress) {
        return new SignUpViaEmailRequestBuilder(emailAddress).build();
    }

    @Override
    protected SignUpViaEmailRequestBuilder getThis() {
        return this;
    }

    private class BodyParameters {
        public static final String EMAIL = "email";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PASSWORD = "password";
        public static final String PROFILE_IMAGE_URL = "profileImageUrl";
    }
}
