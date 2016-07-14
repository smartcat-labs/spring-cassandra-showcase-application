package cucumber.api.cli.springboot;

import cucumber.runtime.ClasspathClassFinder;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ClasspathResourceLoader;
import java.io.IOException;
import java.util.List;
import cucumber.runtime.Runtime;

public class CucumberSpringBootCliRunner {

    private final ClassLoader classLoader;
    private final List<String> args;
    private int exitCode = 0;

    public CucumberSpringBootCliRunner(ClassLoader classLoader, List<String> args) {
        this.classLoader = classLoader;
        this.args = args;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void run() throws IOException {
        final Runtime runtime = new Runtime(new ClasspathResourceLoader(classLoader),
            new ClasspathClassFinder(classLoader), classLoader, new RuntimeOptions(args));
        runtime.run();
        exitCode = runtime.exitStatus();
    }
}
