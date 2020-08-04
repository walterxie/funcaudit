package audit.beast2;

import audit.AbstractClassLoader;
import beast.util.PackageManager;

import java.util.List;

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

    public static void main(String[] args) {

        B2ClassLoader b2loader = new B2ClassLoader();
        List<String> listBON = b2loader.getChildClassNames(beast.core.BEASTObject.class, "evolution");

        b2loader.getChildClassNames(beast.evolution.alignment.Alignment.class, null);

        b2loader.getChildClassNames(beast.evolution.substitutionmodel.SubstitutionModel.class, null);

        b2loader.getChildClassNames(beast.evolution.sitemodel.SiteModelInterface.class, null);

        b2loader.getChildClassNames(beast.evolution.branchratemodel.BranchRateModel.class, null);

        b2loader.getChildClassNames(beast.math.distributions.ParametricDistribution.class, null);

        b2loader.getChildClassNames(beast.core.parameter.Parameter.class, null);

        List<String> listDist = b2loader.getChildClassNames(beast.core.Distribution.class, null);

        b2loader.getChildClassNames(beast.core.Operator.class, null);

        b2loader.getChildClassNames(beast.evolution.datatype.DataType.class, null);


 /*
        b2loader.getChildClassNames(TreeDistribution.class, null);

        b2loader.getChildClassNames(Prior.class, null);

        b2loader.getChildClassNames(GenericTreeLikelihood.class, null);


        b2loader.getChildClassNames(StateNode.class, null);

        b2loader.getChildClassNames(CalculationNode.class, null);

*/

    }

}

