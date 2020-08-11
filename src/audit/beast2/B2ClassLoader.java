package audit.beast2;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
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
    protected Set<Class<?>> getSubclasses(Class<?> cls) {
//        return PackageManager.find(cls, pkgDir); // load all installed pkgs
        Path jarPath = Paths.get(JarPathString);
        JarFile jarF =  getJarFile(jarPath);
        return getSubclasses(cls, jarF, PKG);
    }

    protected Class[] getClasses() {
        return new Class[]{
//                Alignment.class, SubstitutionModel.class,
//            beast.evolution.sitemodel.SiteModelInterface.class,
//            beast.evolution.branchratemodel.BranchRateModel.class,
//            beast.core.parameter.Parameter.class,
                beast.core.CalculationNode.class,
                beast.core.Distribution.class, beast.core.Function.class };
    }
 /*
             beast.math.distributions.ParametricDistribution.class,
             beast.evolution.datatype.DataType.class
 b2loader.getChildClassNames(beast.core.BEASTObject.class, "evolution");
        beast.core.Operator.class,
        b2loader.getChildClassNames(TreeDistribution.class, null);
        b2loader.getChildClassNames(Prior.class, null);
        b2loader.getChildClassNames(GenericTreeLikelihood.class, null);
        b2loader.getChildClassNames(StateNode.class, null);
        b2loader.getChildClassNames(CalculationNode.class, null);
*/

    @Override
    protected Class[] getExclClasses() {
        return new Class[]{beast.math.distributions.ParametricDistribution.class,
                // BranchRateModel is a prior, incl in PhyloCTMCToBEAST
                beast.evolution.branchratemodel.BranchRateModel.class,
                beast.evolution.branchratemodel.RateStatistic.class};
    }

    @Override
    protected String[] getExclPackages() {
        return new String[]{"beast.app","beast.core.parameter","beast.evolution.likelihood",
                // this rm all starting with -.-.-.Tree, such as TreeStatLogger, TreeDistribution
                "beast.evolution.tree.Tree"};
    }

    @Override
    protected String getTitle() {
        return "BEAST 2";
    }


    public static void main(String[] args) {

        AbstractClassLoader b2loader = new B2ClassLoader();
        Map<Class<?>, Set<Class<?>>> inheritMap = b2loader.getInheritanceMap();

        try {
            b2loader.writeMarkdown("beast2.md", "BEAST 2", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

