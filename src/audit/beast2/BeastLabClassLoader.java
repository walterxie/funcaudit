package audit.beast2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * @author Walter Xie
 */
public class BeastLabClassLoader extends B2ClassLoader {

    protected String JarPathString = System.getProperty("user.home") +
            "/WorkSpace/linguaPhylo/lphybeast/lib/BEASTlabs.addon.jar";

    @Override
    protected Class[] getClasses() {
        return super.getClasses();
    }

    @Override
    protected Set<Class<?>> getSubclasses(Class<?> cls) {
        Path jarPath = Paths.get(JarPathString);
        JarFile jarF =  getJarFile(jarPath);
        return getSubclasses(cls, jarF, PKG);
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
        return "BEAST Lab";
    }
}
