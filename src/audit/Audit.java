/**
 * @author - Walter Xie
 */


package audit;

import audit.beast2.BeastLabClassLoader;
import audit.beast2.MascotClassLoader;
import audit.lphy.LPhyBEASTClassHelper;
import audit.lphy.LPhyBEASTClassLoader;
import audit.lphy.LPhyClassLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class Audit {
    final AbstractClassLoader lphybeastloader;
    final AbstractClassLoader lphyloader;
    final AbstractClassLoader b2loader;

    // b2loader could be any beast2 pkg
    public Audit(AbstractClassLoader b2loader) {
        lphybeastloader = new LPhyBEASTClassLoader();
        lphyloader = new LPhyClassLoader();
        this.b2loader = b2loader;
    }

    public void writeMarkdown(String fn, String[] titles) {
        Map<Class<?>, Set<Class<?>>> lphybeastInheritMap = lphybeastloader.getInheritanceMap();
        Map<Class<?>, Set<Class<?>>> lphyInheritMap = lphyloader.getInheritanceMap();
        Map<Class<?>, Set<Class<?>>> beastInheritMap = b2loader.getInheritanceMap();

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> lPhyClassMap = helper.createLPhyClassMap(lphybeastInheritMap);
        // Class<?> lphybeast <=> Class<?> beast
        Map<Class<?>, Class<?>> beastClassMap = helper.createBEASTClassMap(lphybeastInheritMap);
        try {
            PrintWriter out = new PrintWriter(fn);
            helper.writeResultTable(out, titles, lphybeastInheritMap, lphyInheritMap, beastInheritMap,
                    lPhyClassMap, beastClassMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        AbstractClassLoader loader;
        Audit audit;

        // BEAST
//        loader = new B2ClassLoader();
//        audit = new Audit(loader);
//        audit.writeMarkdown("lphybeast.md", new String[]{"LPhyBEAST","LPhy","BEAST 2"});

        // BEAST Lab
        loader = new BeastLabClassLoader();
        audit = new Audit(loader);
        audit.writeMarkdown("lphybeastlab.md", new String[]{"LPhyBEAST","LPhy","BEAST Lab"});

        // contraband
//        loader = new ContrabandClassLoader();
//        audit = new Audit(loader);
//        audit.writeMarkdown("lphycontraband.md", new String[]{"LPhyBEAST","LPhy","contraband"});

        // Mascot
        loader = new MascotClassLoader();
        audit = new Audit(loader);
        audit.writeMarkdown("lphymascot.md", new String[]{"LPhyBEAST","LPhy","Mascot"});

    }

}


