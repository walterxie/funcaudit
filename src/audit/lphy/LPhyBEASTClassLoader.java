package audit.lphy;

import audit.AbstractClassLoader;
import audit.beast2.B2ClassLoader;

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
        Map<Class<?>, List<Class<?>>> lpbInheritMap = loader.getInheritanceMap();

        AbstractClassLoader loader2 = new LPhyClassLoader();
        Map<Class<?>, List<Class<?>>> lphyInheritMap = loader2.getInheritanceMap();

        AbstractClassLoader b2loader = new B2ClassLoader();
        Map<Class<?>, List<Class<?>>> beastInheritMap = b2loader.getInheritanceMap();

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> lPhyClassMap = helper.createLPhyClassMap(lpbInheritMap);
        // Class<?> lphybeast <=> Class<?> beast
        Map<Class<?>, Class<?>> beastClassMap = helper.createBEASTClassMap(lpbInheritMap);
        try {
            helper.writeMarkdown("lphybeast.md", new String[]{"LPhyBEAST","LPhy","BEAST 2"},
                    lpbInheritMap, lphyInheritMap, beastInheritMap, lPhyClassMap, beastClassMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

