package cucumber.runtime.io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;

public class ClasspathResourceLoader implements ResourceLoader {

    public static final String CLASSPATH_SCHEME = "classpath:";

    private final ClassLoader classLoader;

    public ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Iterable<Resource> resources(String path, String suffix) {
        String packageName = stripClasspathPrefix(path).replace("/", ".");
        if (suffix.equals(".class")) {
            return loadClasses(suffix, packageName);
        } else {
            return loadResources(suffix, packageName);
        }
    }

    private Iterable<Resource> loadClasses(String suffix, String packageName) {
        List<Resource> resources = new ArrayList<>();
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        reflections.getSubTypesOf(Object.class).stream().map(
            (clazz) -> clazz.getName()).forEach((name) -> {
                resources.add(new ClasspathResource(name, suffix, classLoader));
            });
        return resources;
    }

    private Iterable<Resource> loadResources(String suffix, String packageName) {
        List<Resource> resources = new ArrayList<>();
        Reflections reflections = new Reflections(packageName, new ResourcesScanner());
        reflections.getResources(Pattern.compile(String.format(
            ".*?\\%s.*", suffix))).stream().forEach((resourceLocation) -> {
                resources.add(new ClasspathResource(resourceLocation, suffix, classLoader));
            });
        return resources;
    }

    private static String stripClasspathPrefix(String path) {
        return path.substring(CLASSPATH_SCHEME.length());
    }
}
