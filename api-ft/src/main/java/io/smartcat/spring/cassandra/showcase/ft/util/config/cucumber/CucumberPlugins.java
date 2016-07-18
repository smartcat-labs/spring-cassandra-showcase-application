package io.smartcat.spring.cassandra.showcase.ft.util.config.cucumber;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cucumber")
public class CucumberPlugins {

    private List<String> plugins = new ArrayList<>();

    public List<String> getPlugins() {
        return plugins;
    }
}
