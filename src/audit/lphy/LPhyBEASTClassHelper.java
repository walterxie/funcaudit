package audit.lphy;

import lphybeast.GeneratorToBEAST;
import lphybeast.ValueToBEAST;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class LPhyBEASTClassHelper {

    public LPhyBEASTClassHelper() {
    }

    public Class<?> getLPhyClass(Class<?> lphybeast){

        String methodName;
        if (GeneratorToBEAST.class.isAssignableFrom(lphybeast)) {
            methodName = "getGeneratorClass";
        } else if (ValueToBEAST.class.isAssignableFrom(lphybeast)) {
            methodName = "getValueClass";
        } else {
            throw new UnsupportedOperationException("Cannot handle " + lphybeast);
        }

        Method method = null;
        Object lphy = null;
        try {
            method = lphybeast.getMethod(methodName);
            // need an instance of it
            lphy = method.invoke(lphybeast.getConstructor().newInstance());
        } catch (Exception e) {
            System.err.println(lphybeast);
            System.err.println(method);
            e.printStackTrace();
        }
        return (Class<?>) lphy;
    }

    /**
     * Class<?> lphybeast <=> Class<?> lphy
     * @param lphybeInheritMap  lphybeast
     * @return
     */
    public Map<Class<?>, Class<?>> createClassMap(Map<Class<?>, List<Class<?>>> lphybeInheritMap) {
        Map<Class<?>, Class<?>> clsMap = new LinkedHashMap();

        for (Map.Entry<Class<?>, List<Class<?>>> entry : lphybeInheritMap.entrySet()) {

            for (Class<?> lphybeast : entry.getValue()) {
                Class<?> lphy = getLPhyClass(lphybeast);
                assert lphy != null;

                clsMap.put(lphybeast, lphy);
            }
        }

        return clsMap;
    }

    // TODO limit to inner_join
    public void writeMarkdown(String fn, String[] title, Map<Class<?>, List<Class<?>>> inheritMap,
                              Map<Class<?>, Class<?>> clsMap) throws FileNotFoundException {

        assert title.length >= 2;
        try (PrintWriter out = new PrintWriter(fn)) {

            out.println("| " + title[0] + " | " + title[1] + " |");
            out.println("| ------- | ------- |");

            for (Map.Entry<Class<?>, List<Class<?>>> entry : inheritMap.entrySet()) {
                Class<?> key = entry.getKey();
                List<Class<?>> classes = entry.getValue();

                out.println("| **" + key.getName() + "** |  |");
                for (Class<?> cls : classes) {
                    String col2 = "";
                    Class<?> cls2 = clsMap.get(cls);
                    if (cls2 != null) {
                        col2 = cls2.getName();
                    }
                    out.println("| " + cls.getName() + " | " + col2 + " |");
                }

            }
        }
    }


//    public BEASTInterface getBEASTClass(GeneratorToBEAST lphybeast){
//        return lphybeast.generatorToBEAST().getClass();
//    }


}
