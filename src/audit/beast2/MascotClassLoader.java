package audit.beast2;

import audit.AbstractClassLoader;

/**
 * @author Walter Xie
 */
public class MascotClassLoader extends AbstractClassLoader {


    @Override
    protected String[] getPkgNames() {
        return new String[]{"beast","mascot"};
    }

    @Override
    protected Class[] getSuperClasses() {
        return new Class[]{beast.core.CalculationNode.class,
                beast.core.Distribution.class, beast.core.Function.class};
    }

    @Override
    protected String getPathString() {
        return MY_PATH + "linguaPhylo/lphybeast/lib/Mascot.v2.1.2.jar";
    }

    @Override
    protected Class[] getExclClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getExclStartWith() {
        return new String[0];
    }

    @Override
    public String getTitle() {
        return "Mascot";
    }
}
