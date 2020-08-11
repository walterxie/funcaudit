package audit.lphy;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

/**
 * @author Walter Xie
 */
public class LPhyClassLoader extends AbstractClassLoader {

    protected String PKG = "lphy";
    final String CLSPathString = System.getProperty("user.home") +
            "/WorkSpace/linguaPhylo/build";

    @Override
    protected Set<Class<?>> getSubclasses(Class<?> cls) {
        Path dir = Paths.get(CLSPathString);
        return getSubclasses(cls, dir, PKG);
    }

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphy.graphicalModel.Generator.class,lphy.graphicalModel.Value.class};
    }

    @Override
    protected Class[] getExclClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getExclStartWith() {
        return new String[]{"lphy.graphicalModel.types","lphy.parser"};
    }

    @Override
    protected String getTitle() {
        return "LPhy";
    }


    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyClassLoader();
        Map<Class<?>, Set<Class<?>>> inheritMap = loader.getInheritanceMap();

        try {
            loader.writeMarkdown("lphy.md", "Lphy", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

