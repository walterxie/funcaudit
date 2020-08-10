package audit.lphy;

import beast.core.BEASTInterface;
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
    public Map<Class<?>, Class<?>> createLPhyClassMap(Map<Class<?>, List<Class<?>>> lphybeInheritMap) {
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

    public Map<Class<?>, Class<?>> createBEASTClassMap(Map<Class<?>, List<Class<?>>> lphybeInheritMap) {
        Map<Class<?>, Class<?>> clsMap = new LinkedHashMap();

        for (Map.Entry<Class<?>, List<Class<?>>> entry : lphybeInheritMap.entrySet()) {

            for (Class<?> lphybeast : entry.getValue()) {
                Class<BEASTInterface> beast = getBEASTClass(lphybeast);
                assert beast != null;

                clsMap.put(lphybeast, beast);
            }
        }
        return clsMap;
    }

    // outer_join
    public void writeMarkdown(String fn, String[] title,
                              final Map<Class<?>, List<Class<?>>> lphybeastInheritMap,
                              final Map<Class<?>, List<Class<?>>> lphyInheritMap,
                              final Map<Class<?>, List<Class<?>>> beastInheritMap,
                              final Map<Class<?>, Class<?>> lphyClassMap,
                              final Map<Class<?>, Class<?>> beastClassMap)
            throws FileNotFoundException {

        assert title.length == 3;
        try (PrintWriter out = new PrintWriter(fn)) {

            out.println(title[0] + " | " + title[1] + " | " + title[2]);
            out.println("--- | --- | ---");

            // left join to inheritMap
            List<Class<?>> cls2List = new ArrayList<>();
            List<Class<?>> cls3List = new ArrayList<>();
            for (Map.Entry<Class<?>, List<Class<?>>> entry : lphybeastInheritMap.entrySet()) {
                Class<?> key = entry.getKey();
                List<Class<?>> classes = entry.getValue();

                out.println("**" + key.getName() + "** |  |  ");
                for (Class<?> cls : classes) {
                    String col2 = "";
                    Class<?> cls2 = lphyClassMap.get(cls);
                    if (cls2 != null)
                        col2 = cls2.getName();

                    String col3 = "";
                    Class<?> cls3 = beastClassMap.get(cls);
                    if (cls3 != null)
                        col3 = cls3.getName();

                    out.println(cls.getName() + " | " + col2 + " | " + col3 );

                    cls2List.add(cls2);
                    cls3List.add(cls3);
                }
            }

            out.println("**Suspected missing parser** | **Implemented LPhy** | **Implemented BEAST**");

            // add the rest to make union
            List<Class<?>> restList = getExcludedClassList(cls2List, lphyInheritMap);
            if (restList.size() > 0) {
                for (Class<?> cls : restList) {
                    out.println("|  | " + cls.getName() + " | ");
                }
            }//TODO BEAST rest of matching LPhy

            //TODO make better BEAST rest
            restList = getExcludedClassList(cls3List, beastInheritMap);
            if (restList.size() > 0) {
                for (Class<?> cls : restList) {
                    out.println("|  |  | " + cls.getName() );
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

        Object lphy = getObjectFromMethod(lphybeast, methodName);
        return (Class<?>) lphy;
    }

    private Class<BEASTInterface> getBEASTClass(Class<?> lphybeast){
        String methodName;
        if (GeneratorToBEAST.class.isAssignableFrom(lphybeast) || ValueToBEAST.class.isAssignableFrom(lphybeast)) {
            methodName = "getBEASTClass";
        } else {
            throw new UnsupportedOperationException("Cannot handle " + lphybeast);
        }

        Object beast = getObjectFromMethod(lphybeast, methodName);
        return (Class<BEASTInterface>) beast;
    }


    private Object getObjectFromMethod(Class<?> lphybeast, String methodName) {
        Method method = null;
        Object obj = null;
        try {
            method = lphybeast.getMethod(methodName);
            // need an instance of it
            obj = method.invoke(lphybeast.getConstructor().newInstance());
        } catch (Exception e) {
            System.err.println(lphybeast);
            System.err.println(method);
            e.printStackTrace();
        }
        return obj;
    }

}
