package audit.lphy;

import lphybeast.GeneratorToBEAST;
import lphybeast.ValueToBEAST;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Walter Xie
 */
public class LPhyBEASTClassHelper {

    public LPhyBEASTClassHelper() {
    }


    /**
     * Create class mapping from LPhyBEAST, by looking up
     * {@link GeneratorToBEAST#getGeneratorClass()} and
     * {@link ValueToBEAST#getValueClass()}.
     * @param lphybeInheritMap  lphybeast
     * @return  map of Class<?> lphybeast <=> Class<?> lphy
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

    // outer_join
    public void writeMarkdown(String fn, String[] title, final Map<Class<?>, List<Class<?>>> inheritMap,
                              final Map<Class<?>, List<Class<?>>> inheritMap2, final Map<Class<?>, Class<?>> clsMap)
            throws FileNotFoundException {

        assert title.length >= 2;
        try (PrintWriter out = new PrintWriter(fn)) {

            out.println("| " + title[0] + " | " + title[1] + " |");
            out.println("| ------- | ------- |");

            // left join to inheritMap
            List<Class<?>> cls2List = new ArrayList<>();
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

                    cls2List.add(cls2);
                }
            }

            // add the rest to make union
            List<Class<?>> restList = getExcludedClassList(cls2List, inheritMap2);
            if (restList.size() > 0) {
                out.println("| **Suspected missing parser** | **Implemented LPhy** |");
                for (Class<?> cls : restList) {
                    out.println("|  | " + cls.getName() + " |");
                }
            }

        }
    }



    private List<Class<?>> getExcludedClassList(List<Class<?>> clsInclList,
                                                Map<Class<?>, List<Class<?>>> inheritMap) {
        List<Class<?>> exclList = new ArrayList<>();

        for (Map.Entry<Class<?>, List<Class<?>>> entry : inheritMap.entrySet()) {
//            Class<?> key = entry.getKey(); //TODO need incl key?
            List<Class<?>> classes = entry.getValue();

            for (Class<?> cls : classes) {
                if (!clsInclList.contains(cls)) {
                    exclList.add(cls);
                }
            }
        }

        return exclList;
    }


    private Class<?> getLPhyClass(Class<?> lphybeast){
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



//    public BEASTInterface getBEASTClass(GeneratorToBEAST lphybeast){
//        return lphybeast.generatorToBEAST().getClass();
//    }


}
