package audit.beast2;

import audit.AbstractClassLoader;
import beast.evolution.alignment.Alignment;
import beast.evolution.substitutionmodel.SubstitutionModel;
import beast.util.PackageManager;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class B2ClassLoader extends AbstractClassLoader {

    final String[] pkgDir;
//    final static String PKG = "beast";
//    final static String JarPathString = System.getProperty("user.home") +
//            "/WorkSpace/linguaPhylo/build-lib/beast.jar";

    public B2ClassLoader() {
        this(new String[]{"beast"});
    }

    public B2ClassLoader(String[] pkgDir) {
        this.pkgDir = pkgDir;
    }

    @Override
    protected List<String> getSubcls(Class<?> cls) {
        return PackageManager.find(cls, pkgDir);
    }

    public Map<String, List<String>> getClassMap() {
        Map<String, List<String>> clsMap = new HashMap();

//        List<String> listBON = getChildClassNames(beast.core.BEASTObject.class, "evolution");
//        clsMap.put("beast.core.BEASTObject", listBON);

        Class[] classes = new Class[]{Alignment.class, SubstitutionModel.class,
                beast.evolution.sitemodel.SiteModelInterface.class,
                beast.evolution.branchratemodel.BranchRateModel.class,
                beast.math.distributions.ParametricDistribution.class,
                beast.core.parameter.Parameter.class,
                beast.core.Distribution.class,
                beast.core.Operator.class,
                beast.evolution.datatype.DataType.class
        };

        for (Class cls : classes) {
            List<String> listClsNm = getChildClassNames(cls, null);
            String key = cls.getName();
            clsMap.put(key, listClsNm);
        }

        return clsMap;
 /*
        b2loader.getChildClassNames(TreeDistribution.class, null);
        b2loader.getChildClassNames(Prior.class, null);
        b2loader.getChildClassNames(GenericTreeLikelihood.class, null);
        b2loader.getChildClassNames(StateNode.class, null);
        b2loader.getChildClassNames(CalculationNode.class, null);
*/
    }

    public void writeMarkdown (String fn, Map<String, List<String>> clsMap) throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(fn)) {

            out.println("| BEAST 2 |");
            out.println("| ------- |");

            for (Map.Entry<String, List<String>> entry : clsMap.entrySet()) {
                String key = entry.getKey();
                List<String> classes = entry.getValue();

                out.println("| " + key + " |");
                for (String cls : classes) {
                    out.println("| " + cls + " |");
                }

            }

        }

    }


    public static void main(String[] args) {

        B2ClassLoader b2loader = new B2ClassLoader();

        Map<String, List<String>> clsMap = b2loader.getClassMap();

        try {
            b2loader.writeMarkdown("beast2.md", clsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

