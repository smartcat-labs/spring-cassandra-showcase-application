package cucumber.runtime.io;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathResource implements Resource {
    private final String path;
    private final String className;
    private final ClassLoader classLoader;

    public ClasspathResource(String className, String suffix, ClassLoader classLoader) {
        this.path = className + suffix;
        this.className = className;
        this.classLoader = classLoader;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getAbsolutePath() {
        return path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return classLoader.getResourceAsStream(className);
    }

    @Override
    public String getClassName(String extension) {
        String path = getPath();
        return path.substring(0, path.length() - extension.length()).replace('/', '.');
    }

}
