package audit.lphy;

import audit.AbstractClassLoader;
import audit.beast2.B2ClassLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * @author Walter Xie
 */
public class LPhyBEASTClassLoader extends LPhyClassLoader {

    protected String PKG = "lphybeast";

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphybeast.GeneratorToBEAST.class,lphybeast.ValueToBEAST.class};
    }

    @Override
    protected String getTitle() {
        return "LPhy to BEAST 2";
    }

    // Note: need to run ant build to refresh the lphy and lphybeast classes
    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyBEASTClassLoader();
        Map<Class<?>, Set<Class<?>>> lphybeastInheritMap = loader.getInheritanceMap();

        AbstractClassLoader loader2 = new LPhyClassLoader();
        Map<Class<?>, Set<Class<?>>> lphyInheritMap = loader2.getInheritanceMap();

        AbstractClassLoader b2loader = new B2ClassLoader();
        Map<Class<?>, Set<Class<?>>> beastInheritMap = b2loader.getInheritanceMap();

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> lPhyClassMap = helper.createLPhyClassMap(lphybeastInheritMap);
        // Class<?> lphybeast <=> Class<?> beast
        Map<Class<?>, Class<?>> beastClassMap = helper.createBEASTClassMap(lphybeastInheritMap);
        try {
            PrintWriter out = new PrintWriter("lphybeast.md");
            helper.writeResultTable(out, new String[]{"LPhyBEAST","LPhy","BEAST 2"},
                    lphybeastInheritMap, lphyInheritMap, beastInheritMap, lPhyClassMap, beastClassMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

