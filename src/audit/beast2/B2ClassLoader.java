package audit.beast2;

import audit.AbstractClassLoader;
import beast.evolution.alignment.Alignment;
import beast.evolution.substitutionmodel.SubstitutionModel;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * @author Walter Xie
 */
public class B2ClassLoader extends AbstractClassLoader {

//    final String[] pkgDir;
    final String PKG = "beast";
    final String JarPathString = System.getProperty("user.home") +
            "/WorkSpace/linguaPhylo/lphybeast/lib/beast.jar";


    @Override
    protected List<Class<?>> getSubclasses(Class<?> cls) {
//        return PackageManager.find(cls, pkgDir); // load all installed pkgs
        Path jarPath = Paths.get(JarPathString);
        JarFile jarF =  getJarFile(jarPath);
        return getSubclasses(cls, jarF, PKG);
    }

    protected Class[] getClasses() {
        return new Class[]{Alignment.class, SubstitutionModel.class,
            beast.evolution.sitemodel.SiteModelInterface.class,
            beast.evolution.branchratemodel.BranchRateModel.class,
            beast.math.distributions.ParametricDistribution.class,
            beast.core.parameter.Parameter.class,
            beast.core.Distribution.class
    };}
 /*
             beast.evolution.datatype.DataType.class
 b2loader.getChildClassNames(beast.core.BEASTObject.class, "evolution");
        beast.core.Operator.class,
        b2loader.getChildClassNames(TreeDistribution.class, null);
        b2loader.getChildClassNames(Prior.class, null);
        b2loader.getChildClassNames(GenericTreeLikelihood.class, null);
        b2loader.getChildClassNames(StateNode.class, null);
        b2loader.getChildClassNames(CalculationNode.class, null);
*/


    public static void main(String[] args) {

        AbstractClassLoader b2loader = new B2ClassLoader();
        Map<Class<?>, List<Class<?>>> inheritMap = b2loader.getInheritanceMap();

        try {
            b2loader.writeMarkdown("beast2.md", "BEAST 2", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

