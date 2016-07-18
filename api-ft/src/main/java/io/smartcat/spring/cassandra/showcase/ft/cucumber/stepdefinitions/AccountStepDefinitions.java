package io.smartcat.spring.cassandra.showcase.ft.cucumber.stepdefinitions;

import static io.smartcat.spring.cassandra.showcase.ft.account.AccountGenerators.givenUniqueEmail;
import static io.smartcat.spring.cassandra.showcase.ft.account.SignUpViaEmailRequestBuilder.aSignUpViaEmailRequest;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.spring.context.initializer.CucumberApplicationContextInitializer;
import io.smartcat.spring.cassandra.showcase.ft.account.Account;

@ContextConfiguration(initializers = { CucumberApplicationContextInitializer.class })
public class AccountStepDefinitions {

    private String usedEmail;
    private Response response;

    @When("^a valid sign up via email address request is sent$")
    public void a_valid_sign_up_via_email_address_request_is_sent() throws Throwable {
        usedEmail = givenUniqueEmail();
        response = aSignUpViaEmailRequest(usedEmail).invoke();
    }

    @Then("^the response status code should be (\\d+)$")
    public void the_response_status_code_should_be(final int expectedStatusCode) throws Throwable {
        assertThat(response.getStatus()).isEqualTo(expectedStatusCode);
    }

    @And("^account with that email address is created$")
    public void account_with_that_email_address_is_created() throws Throwable {
        assertThat(Account.fromResponse(response).getEmail()).isEqualTo(usedEmail);
    }
}
