package audit.lphy;

import audit.AbstractClassLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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


    public static void main(String[] args) throws Exception {

        LPhyClassLoader loader = new LPhyClassLoader();
        List<String> listLPN = loader.getChildClassNames(lphy.graphicalModel.Generator.class, null);

        loader.getChildClassNames(lphy.graphicalModel.GraphicalModelNode.class, null);


    }

}

