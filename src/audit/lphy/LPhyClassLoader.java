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

    final static String PKG = "lphy";
    final static String CLSPathString = System.getProperty("user.home") +
            "/WorkSpace/linguaPhylo/build";

    @Override
    protected List<String> getSubcls(Class<?> cls) {
        Path dir = Paths.get(CLSPathString);
        return getSubcls(cls, dir, PKG);
    }

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphy.graphicalModel.Generator.class};
    }

    public static void main(String[] args) {

        LPhyClassLoader lphyloader = new LPhyClassLoader();
        Map<String, List<String>> clsMap = lphyloader.getClassMap();

        try {
            lphyloader.writeMarkdown("lphy.md", "Lphy", clsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

