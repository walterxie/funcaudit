package audit.beast2;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

/**
 * @author Walter Xie
 */
public class B2ClassLoader extends AbstractClassLoader {

    @Override
    protected String getPathString() {
        return MY_PATH + "linguaPhylo/lphybeast/lib/beast.jar";
    }

    @Override
    protected String[] getPkgNames() {
        return new String[]{"beast"};
    }

    protected Class[] getSuperClasses() {
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
    protected String[] getExclStartWith() {
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

