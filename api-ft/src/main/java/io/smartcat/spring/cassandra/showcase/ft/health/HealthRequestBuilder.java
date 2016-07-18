package io.smartcat.spring.cassandra.showcase.ft.health;

import javax.ws.rs.client.Invocation;

import io.smartcat.spring.cassandra.showcase.ft.common.RequestBuilder;

public class HealthRequestBuilder extends RequestBuilder<HealthRequestBuilder> {

    public HealthRequestBuilder() {
        super();
        withHttpMethod(HttpMethod.GET);
        withEndPoint("health");
    }

    public static Invocation aHealthRequest() {
        return new HealthRequestBuilder().build();
    }

    @Override
    protected HealthRequestBuilder getThis() {
        return this;
    }
}
