package audit.beast2;

import beast.util.PackageManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Walter Xie
 */
public class B2ClassLoader {

    final String[] pkgDir;


    public B2ClassLoader() {
        this(new String[]{"beast"});
    }

    public B2ClassLoader(String[] pkgDir) {
        this.pkgDir = pkgDir;
    }

    public List<String> getChildClassNames(Class<?> cls, String regxContain) {
        List<String> listCCN = PackageManager.find(cls, pkgDir);
        // rm itself
        listCCN.removeIf(s -> s.equals(cls));
        // contains, if regx not null
        if (Objects.nonNull(regxContain)) {
            listCCN.removeIf(s -> !s.contains(regxContain));
        }

        System.out.println(cls.getSimpleName() + " child class = " + listCCN.size());
        System.out.println(Arrays.toString(listCCN.toArray()));
        return (listCCN);
    }


    public static void main(String[] args) throws Exception {

        B2ClassLoader b2loader = new B2ClassLoader();
        List<String> listBON = b2loader.getChildClassNames(beast.core.BEASTObject.class, "evolution");

        b2loader.getChildClassNames(beast.evolution.alignment.Alignment.class, null);

        b2loader.getChildClassNames(beast.evolution.substitutionmodel.SubstitutionModel.class, null);

        b2loader.getChildClassNames(beast.evolution.sitemodel.SiteModelInterface.class, null);

        b2loader.getChildClassNames(beast.evolution.branchratemodel.BranchRateModel.class, null);

        List<String> listDist = b2loader.getChildClassNames(beast.core.Distribution.class, null);

        b2loader.getChildClassNames(beast.core.parameter.Parameter.class, null);

        b2loader.getChildClassNames(beast.core.Operator.class, null);


 /*
        b2loader.getChildClassNames(TreeDistribution.class, null);

        b2loader.getChildClassNames(Prior.class, null);

        b2loader.getChildClassNames(GenericTreeLikelihood.class, null);


        b2loader.getChildClassNames(StateNode.class, null);

        b2loader.getChildClassNames(CalculationNode.class, null);

*/


    }

}

