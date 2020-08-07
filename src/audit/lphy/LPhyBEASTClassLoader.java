package audit.lphy;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class LPhyBEASTClassLoader extends LPhyClassLoader {

    protected String PKG = "lphybeast";

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphybeast.GeneratorToBEAST.class,lphybeast.ValueToBEAST.class};
    }

    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyBEASTClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMap = loader.getInheritanceMap();

        try {
            loader.writeMarkdown("lphybeast.md", "LphyBEAST", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

