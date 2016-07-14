package io.smartcat.spring.cassandra.showcase.ft.util.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.smartcat.spring.cassandra.showcase.ft.util.config.cucumber.CucumberConfig;
import io.smartcat.spring.cassandra.showcase.ft.util.config.server.ServerConfig;

/**
 * Global configuration class.
 */
@Component
public class ApplicationConfig {

    @Autowired
    private CucumberConfig cucumberConfig;

    @Autowired
    private ServerConfig serverConfig;

    public CucumberConfig getCucumberConfig() {
        return cucumberConfig;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

}
