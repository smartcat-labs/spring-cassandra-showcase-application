package cucumber.runtime;

import java.util.Collection;
import org.reflections.Reflections;

public class ClasspathClassFinder implements ClassFinder {

    private final ClassLoader classLoader;

    public ClasspathClassFinder(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public <T> Collection<Class<? extends T>> getDescendants(Class<T> parentType,
            String packageName) {
        return new Reflections(packageName).getSubTypesOf(parentType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<? extends T> loadClass(String className) throws ClassNotFoundException {
        return (Class<? extends T>) classLoader.loadClass(className);
    }

}
