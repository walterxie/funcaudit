/**
 * @author - Walter Xie
 */


package audit;

import audit.lphy.LPhyBEASTClassHelper;
import audit.lphy.LPhyBEASTClassLoader;
import audit.lphy.LPhyClassLoader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Audit {


    public Audit() {
    }

    public static void main(String[] args)  {
        AbstractClassLoader loader = new LPhyBEASTClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMapLPB = loader.getInheritanceMap();

        AbstractClassLoader loader2 = new LPhyClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMapL = loader2.getInheritanceMap();//TODO

        LPhyBEASTClassHelper helper = new LPhyBEASTClassHelper();
        // Class<?> lphybeast <=> Class<?> lphy
        Map<Class<?>, Class<?>> clsMap = helper.createClassMap(inheritMapLPB);
        try {
            helper.writeMarkdown("lb-inner-join.md", new String[]{"LphyBEAST","Lphy"}, inheritMapLPB, clsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


