package audit.lphy;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

/**
 * @author Walter Xie
 */
public class LPhyClassLoader extends AbstractClassLoader {

    /**
     * run ant build to refresh classes in the folder /build
     */
    @Override
    protected String getPathString() {
        return MY_PATH + "linguaPhylo/build";
    }

    @Override
    protected String[] getPkgNames() {
        return new String[]{"lphy"};
    }

    @Override
    protected Class[] getSuperClasses() {
        return new Class[]{lphy.graphicalModel.Generator.class,lphy.graphicalModel.Value.class};
    }

    @Override
    protected Class[] getExclClasses() {
        return new Class[0];//TODO birth death
    }

    @Override
    protected String[] getExclStartWith() {
        return new String[]{"lphy.graphicalModel.types","lphy.parser","lphy.core.distributions"};
    }

    @Override
    public String getTitle() {
        return "LPhy";
    }


    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyClassLoader();
        Map<Class<?>, Set<Class<?>>> inheritMap = loader.getInheritanceMap();

        try {
            loader.writeMarkdown("lphy.md", "Lphy", inheritMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

