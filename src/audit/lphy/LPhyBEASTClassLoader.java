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
        Map<Class<?>, List<Class<?>>> inheritMapLPB = loader.getInheritanceMap();

        AbstractClassLoader loader2 = new LPhyClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMapL = loader2.getInheritanceMap();

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> clsMap = helper.createClassMap(inheritMapLPB);
        try {
            helper.writeMarkdown("lphybeast.md", new String[]{"LphyBEAST","Lphy"},
                    inheritMapLPB, inheritMapL, clsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

