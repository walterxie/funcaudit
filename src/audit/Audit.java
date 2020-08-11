/**
 * @author - Walter Xie
 */


package audit;

import audit.beast2.B2ClassLoader;
import audit.beast2.BeastLabClassLoader;
import audit.lphy.LPhyBEASTClassHelper;
import audit.lphy.LPhyBEASTClassLoader;
import audit.lphy.LPhyClassLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class Audit {
    final AbstractClassLoader lpbeloader;
    final AbstractClassLoader lploader2;
    final AbstractClassLoader b2loader;

    // b2loader could be any beast2 pkg
    public Audit(AbstractClassLoader b2loader) {
        lpbeloader = new LPhyBEASTClassLoader();
        lploader2 = new LPhyClassLoader();
        this.b2loader = b2loader;
    }

    public void writeMarkdown(String fn, String[] titles) {
        Map<Class<?>, Set<Class<?>>> lphyInheritMap = lpbeloader.getInheritanceMap();
        Map<Class<?>, Set<Class<?>>> lpbInheritMap = lploader2.getInheritanceMap();
        Map<Class<?>, Set<Class<?>>> beastInheritMap = b2loader.getInheritanceMap();

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> lPhyClassMap = helper.createLPhyClassMap(lpbInheritMap);
        // Class<?> lphybeast <=> Class<?> beast
        Map<Class<?>, Class<?>> beastClassMap = helper.createBEASTClassMap(lpbInheritMap);
        try {
            PrintWriter out = new PrintWriter(fn);
            helper.writeResultTable(out, titles, lpbInheritMap, lphyInheritMap, beastInheritMap,
                    lPhyClassMap, beastClassMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {

        AbstractClassLoader b2loader = new B2ClassLoader();
        Audit audit = new Audit(b2loader);
        audit.writeMarkdown("lphybeast.md", new String[]{"LPhyBEAST","LPhy","BEAST 2"});

        //TODO beast lab
        AbstractClassLoader blloader = new BeastLabClassLoader();
        audit = new Audit(blloader);
        audit.writeMarkdown("lphybeastlab.md", new String[]{"LPhyBEAST","LPhy","BEAST Lab"});
    }

}


