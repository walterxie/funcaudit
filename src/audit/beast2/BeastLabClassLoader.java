package audit.beast2;

import audit.AbstractClassLoader;

import java.util.Set;

/**
 * @author Walter Xie
 */
public class BeastLabClassLoader extends AbstractClassLoader {

    @Override
    protected Class[] getClasses() {
        return new Class[0];
    }

    @Override
    protected Set<Class<?>> getSubclasses(Class<?> cls) {
        return null;
    }

    @Override
    protected Class[] getExclClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getExclStartWith() {
        return new String[0];
    }

    @Override
    protected String getTitle() {
        return null;
    }
}
