package io.smartcat.spring.cassandra.showcase.ft.util.config.cucumber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class CucumberConfig {

    @Value("#{'${cucumber.tags}'.split(';')}")
    private List<String> tags;

    @Autowired
    private CucumberPlugins plugins;

    @Value("${cucumber.monochrome}")
    private boolean monochrome;

    @Value("${cucumber.strict}")
    private boolean strict;

    @Value("${cucumber.dryRun}")
    private boolean dryRun;

    public List<String> getTags() {
        return tags;
    }

    public List<String> getPlugins() {
        return plugins.getPlugins();
    }

    public boolean isMonochrome() {
        return monochrome;
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isDryRun() {
        return dryRun;
    }
}
