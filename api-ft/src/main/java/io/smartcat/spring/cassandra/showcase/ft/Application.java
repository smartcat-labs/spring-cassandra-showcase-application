package io.smartcat.spring.cassandra.showcase.ft;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cucumber.api.cli.springboot.CucumberSpringBootCliRunner;
import io.smartcat.spring.cassandra.showcase.ft.util.cli.CucumberArgumentsBuilder;
import io.smartcat.spring.cassandra.showcase.ft.util.config.ApplicationConfig;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "io.smartcat.spring.cassandra.showcase.ft",
    "cucumber.api.spring.context"
})
public class Application implements CommandLineRunner {

    private static int exitCode = 0;

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static final int ILLEGAL_ARGUMENTS_EXIT_CODE = 127;

    @Autowired
    private ApplicationConfig applicationConfig;

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
        System.exit(exitCode);
    }

    @Override
    public void run(String... args) throws Exception {
        int exitCode = 0;
        try {
            final List<String> argumentsBuilder = new CucumberArgumentsBuilder()
                .build(applicationConfig.getCucumberConfig(), args);
            final CucumberSpringBootCliRunner runner = new CucumberSpringBootCliRunner(
                getClass().getClassLoader(), argumentsBuilder);
            runner.run();
            exitCode = runner.getExitCode();
        } catch (IllegalArgumentException e) {
            exitCode = ILLEGAL_ARGUMENTS_EXIT_CODE;
            log.error(e.getMessage());
            log.error("Error occured, stopping application");
        }
        Application.exitCode = exitCode;
    }
}
