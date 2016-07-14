package io.smartcat.spring.cassandra.showcase.ft.cucumber.stepdefinitions;

import static io.smartcat.spring.cassandra.showcase.ft.health.HealthRequestBuilder.aHealthRequest;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.spring.context.initializer.CucumberApplicationContextInitializer;
import io.smartcat.spring.cassandra.showcase.ft.health.Health;

@ContextConfiguration(initializers = {CucumberApplicationContextInitializer.class})
public class SmokeStepDefinitions {

    private Health health;

    @When("^a request to the health endpoint is sent$")
    public void a_request_to_the_health_endpoint_is_sent() throws Throwable {
        health = Health.from(aHealthRequest());
    }

    @Then("^it should report that status is UP$")
    public void it_should_report_that_status_is_UP() throws Throwable {
        assertThat(health.isUp()).isTrue();
    }
}
