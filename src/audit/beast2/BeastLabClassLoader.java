package audit.beast2;

/**
 * @author Walter Xie
 */
public class BeastLabClassLoader extends B2ClassLoader {

    @Override
    protected String getPathString() {
        return MY_PATH + "linguaPhylo/lphybeast/lib/BEASTlabs.addon.jar";
    }

    @Override
    protected Class[] getSuperClasses() {
        return super.getSuperClasses(); //TODO any to add ?
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
        return "BEAST Lab";
    }
}
