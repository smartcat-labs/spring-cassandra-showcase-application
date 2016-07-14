package io.smartcat.spring.cassandra.showcase.ft.util.cli;

import java.util.ArrayList;
import java.util.List;

import io.smartcat.spring.cassandra.showcase.ft.util.config.cucumber.CucumberConfig;

public class CucumberArgumentsBuilder {

    private static final String TAGS = "--tags";
    private static final String PLUGIN = "--plugin";
    private static final String MONOCHROME = "--monochrome";
    private static final String NO_MONOCHROME = "--no-monochrome";
    private static final String STRICT = "--strict";
    private static final String NO_STRICT = "--no-strict";
    private static final String DRY_RUN = "--dry-run";
    private static final String NO_DRY_RUN = "--no-dry-run";
    private static final String GLUE = "--glue";
    private static final String DEFAULT_GLUE = "io.smartcat.spring.cassandra.showcase.ft.cucumber.stepdefinitions";
    private static final String DEFAULT_FEATURES = "classpath:features";

    /**
     * Builds list of arguments which are meant to be passed to cucumber
     *
     * @param config Cucumber configuration.
     * @param cliArguments Array of command-line arguments.
     * @return
     */
    public List<String> build(CucumberConfig config, String... cliArguments)
        throws IllegalArgumentException {

        List<String> cucumberArguments = new ArrayList<>();
        cucumberArguments.add(DEFAULT_FEATURES);
        addCliArgument(cucumberArguments, GLUE, DEFAULT_GLUE);
        // if tags are passed from command line use those, otherwise use ones from config file
        addParametersFromCommandlineOrConfigFile(cucumberArguments, TAGS, config.getTags(),
            cliArguments);

        config.getPlugins().stream().forEach((plugin) -> {
            addCliArgument(cucumberArguments, PLUGIN, plugin);
        });

        cucumberArguments.add(config.isMonochrome() ? MONOCHROME : NO_MONOCHROME);
        cucumberArguments.add(config.isDryRun() ? DRY_RUN : NO_DRY_RUN);
        cucumberArguments.add(config.isStrict() ? STRICT : NO_STRICT);

        return cucumberArguments;
    }

    /**
     * Searches arguments from command-line for specified parameter name and if found adds it to cucumber runtime
     * configuration, or uses defaults passed in parameter {@code values}. Parameters are expected to be found in array
     * {@code cliArguments} in form of <b>"{parameter, value}"</b>, e.g. {"--tags", "@smoke-test", "--glue",
     * "io.smartcat.spring.cassandra.showcase.ft.cucumber.stepdefinitions", "--tags", "~@ignore"}
     *
     * @param cucumberArguments List of arguments which will be built.
     * @param parameter Name of parameter that's being searched in list of passed parameters (e.g "--tags")
     * @param values List of default values that will be added to arguments list, if parameter with name passed in
     *            {@code parameterName} is not found in {@code cliArguments} variable.
     * @param cliArguments Array of arguments that have been passed on command-line.
     */
    private void addParametersFromCommandlineOrConfigFile(List<String> cucumberArguments,
        String parameter, List<String> values, String... cliArguments)
        throws IllegalArgumentException {
        /*
         If parameter wasn't specified on command line, this variable will stay false.
         This is needed in case parameter is specified multiple times on command-line
         (with different value), and we want to add all of its instances to arguments list.
         For example, "--tags" parameter can be passed on command-line multiple times with
         different values.
         */
        boolean parameterNameFound = false;
        for (int i = 0; i < cliArguments.length; i++) {
            if (cliArguments[i].equals(parameter)) {
                addParameterValuePair(cucumberArguments, i, cliArguments);
                parameterNameFound = true;
            }
        }
        // If parameter was not found in arguments passed on command-line, add default value(s).
        if (!parameterNameFound) {
            addDefaultConfiguration(cucumberArguments, parameter, values);
        }
    }

    /**
     * Adds parameter-value pair read from command line to arguments list.
     *
     * @param cucumberArguments List of parameters and names which is being built.
     * @param pairStartIndex Index where parameter name is found in array of arguments passed form
     * command-line.
     * @param cliArguments Array of arguments received from command line.
     */
    private void addParameterValuePair(List<String> cucumberArguments, int pairStartIndex,
        String... cliArguments) throws IllegalArgumentException {
        if (cliArguments.length < pairStartIndex + 2) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        addCliArgument(cucumberArguments, cliArguments[pairStartIndex],
            cliArguments[pairStartIndex + 1]);
    }

    /**
     * Adds default set of parameter-value pairs to arguments list.
     *
     * @param args List of parameters and names which is being built.
     * @param parameter Name of parameter which we are setting default values for.
     * @param values Values that shall be assigned for specified parameter.
     */
    private void addDefaultConfiguration(List<String> args, String parameter,
        List<String> values) {
        values.stream().forEach((value) -> {
            addCliArgument(args, parameter, value);
        });
    }

    private void addCliArgument(List<String> cucumberArguments, String parameter, String value) {
        cucumberArguments.add(parameter);
        cucumberArguments.add(value);
    }

}
