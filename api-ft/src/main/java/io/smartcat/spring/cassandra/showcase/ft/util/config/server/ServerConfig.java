package io.smartcat.spring.cassandra.showcase.ft.util.config.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    @Value("${server.apiEntryPoint}")
    private String apiEntryPoint;

    public String getApiEntryPoint() {
        return apiEntryPoint;
    }

}
