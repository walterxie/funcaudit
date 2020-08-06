/**
 * @author - Walter Xie
 */


package audit;

import audit.lphy.LPhyBEASTClassLoader;

import java.util.List;
import java.util.Map;

public class Audit {


    public Audit() {
    }




    public static void main(String[] args)  {
        AbstractClassLoader loader = new LPhyBEASTClassLoader();
        Map<Class<?>, List<Class<?>>> clsMap = loader.getClassMap();




    }

}


