package audit.lphy;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class LPhyClassLoader extends AbstractClassLoader {

    protected String PKG = "lphy";
    final String CLSPathString = System.getProperty("user.home") +
            "/WorkSpace/linguaPhylo/build";

    @Override
    protected List<Class<?>> getSubclasses(Class<?> cls) {
        Path dir = Paths.get(CLSPathString);
        return getSubclasses(cls, dir, PKG);
    }

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphy.graphicalModel.Generator.class,lphy.graphicalModel.Value.class};
    }

    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMap = loader.getInheritanceMap();

        try {
            loader.writeMarkdown("lphy.md", "Lphy", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

