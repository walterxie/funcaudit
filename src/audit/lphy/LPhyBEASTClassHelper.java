package audit.lphy;

import lphybeast.GeneratorToBEAST;
import lphybeast.ValueToBEAST;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

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
     *
     * @param lphybeInheritMap lphybeast
     * @return map of Class<?> lphybeast <=> Class<?> lphy
     */
    public Map<Class<?>, Class<?>> createLPhyClassMap(Map<Class<?>, Set<Class<?>>> lphybeInheritMap) {
        Map<Class<?>, Class<?>> clsMap = new LinkedHashMap();

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : lphybeInheritMap.entrySet()) {

            for (Class<?> lphybeast : entry.getValue()) {
                Class<?> lphy = getLPhyClass(lphybeast);
                assert lphy != null;

                clsMap.put(lphybeast, lphy);
            }
        }
        return clsMap;
    }

    public Map<Class<?>, Class<?>> createBEASTClassMap(Map<Class<?>, Set<Class<?>>> lphybeInheritMap) {
        Map<Class<?>, Class<?>> clsMap = new LinkedHashMap();

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : lphybeInheritMap.entrySet()) {

            for (Class<?> lphybeast : entry.getValue()) {
                Class<?> beast = getBEASTClass(lphybeast);
                assert beast != null;

                clsMap.put(lphybeast, beast);
            }
        }
        return clsMap;
    }

    // outer_join
    public void writeResultTable(PrintWriter out, String[] title,
                                 final Map<Class<?>, Set<Class<?>>> lphybeastInheritMap,
                                 final Map<Class<?>, Set<Class<?>>> lphyInheritMap,
                                 final Map<Class<?>, Set<Class<?>>> beastInheritMap,
                                 final Map<Class<?>, Class<?>> lphyClassMap,
                                 final Map<Class<?>, Class<?>> beastClassMap) {

        assert title.length == 3;

        // header
        out.println("<table border=\"1\" width=\"100%\" style=\"margin: 0px;\">");
        out.println("<thead>\n<tr>\n" +
                "<th>" + title[0] + "</th>\n" +
                "<th>" + title[1] + "</th>\n" +
                "<th>" + title[2] + "</th>\n" +
                "</tr>\n</thead>\n");
        // body
        out.println("<tbody>");

        // left join to inheritMap
        Set<Class<?>> cls2Set = new HashSet<>();
        Set<Class<?>> cls3Set = new HashSet<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : lphybeastInheritMap.entrySet()) {
            Class<?> key = entry.getKey();
            Set<Class<?>> classes = entry.getValue();

            out.println("<tr>\n<td><b>" + key.getName() + "</b></td>\n<td></td>\n<td></td>\n</tr>");
            for (Class<?> cls : classes) {
                String col2 = "";
                Class<?> cls2 = lphyClassMap.get(cls);
                if (cls2 != null)
                    col2 = cls2.getName();

                String col3 = "";
                Class<?> cls3 = beastClassMap.get(cls);
                if (cls3 != null)
                    col3 = cls3.getName();

                out.println("<tr>\n<td>" + cls.getName() + "</td>\n<td>" + col2 +
                        "</td>\n<td>" + col3 + "</td>\n</tr>");

                cls2Set.add(cls2);
                cls3Set.add(cls3);
            }
        }

        out.println("<tr>\n<td><b>Suspected missing parser</b></td>\n" +
                "<td><b>Implemented LPhy</b></td>\n" +
                "<td><b>Implemented BEAST</b></td>\n</tr>");

        // add the rest to make union
        Set<Class<?>> restList = getExcludedClassList(cls2Set, lphyInheritMap);
        if (restList.size() > 0) {
            for (Class<?> cls : restList)
                out.println("<tr>\n<td></td>\n<td>" + cls.getName() + "</td>\n<td></td>\n</tr>");
        }//TODO BEAST rest of matching LPhy

        //TODO make better BEAST rest
        restList = getExcludedClassList(cls3Set, beastInheritMap);
        if (restList.size() > 0) {
            for (Class<?> cls : restList)
                out.println("<tr>\n<td></td>\n<td></td>\n<td>" + cls.getName() + "</td>\n</tr>");
        }

        out.println("</tbody>\n</table>");

        out.flush();
        out.close();
    }


    private Set<Class<?>> getExcludedClassList(Set<Class<?>> clsInclSet,
                                                Map<Class<?>, Set<Class<?>>> inheritMap) {
        Set<Class<?>> exclSet = new LinkedHashSet<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : inheritMap.entrySet()) {
//            Class<?> key = entry.getKey(); //TODO need incl key?
            Set<Class<?>> classes = entry.getValue();

            for (Class<?> cls : classes) {
                if (!clsInclSet.contains(cls)) {
                    exclSet.add(cls);
                }
            }
        }

        return exclSet;
    }


    private Class<?> getLPhyClass(Class<?> lphybeast) {
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

    private Class<?> getBEASTClass(Class<?> lphybeast) {
        String methodName;
        if (GeneratorToBEAST.class.isAssignableFrom(lphybeast) || ValueToBEAST.class.isAssignableFrom(lphybeast)) {
            methodName = "getBEASTClass";
        } else {
            throw new UnsupportedOperationException("Cannot handle " + lphybeast);
        }

        Object beast = getObjectFromMethod(lphybeast, methodName);
        return (Class<?>) beast;
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
