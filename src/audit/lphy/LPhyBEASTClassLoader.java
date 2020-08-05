package audit.lphy;

import audit.AbstractClassLoader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class LPhyBEASTClassLoader extends LPhyClassLoader {

    protected String PKG = "lphybeast";

    @Override
    protected Class[] getClasses() {
        return new Class[]{lphybeast.GeneratorToBEAST.class};
    }

    public static void main(String[] args) {

        AbstractClassLoader loader = new LPhyBEASTClassLoader();
        Map<String, List<String>> clsMap = loader.getClassMap();

        try {
            loader.writeMarkdown("lphybeast.md", "LphyBEAST", clsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

