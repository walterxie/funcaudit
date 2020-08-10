package audit;

import beast.core.util.Log;
import beast.util.PackageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * @author Walter Xie
 */
public abstract class AbstractClassLoader {

    File jarFile;
    protected URLClassLoader loader;


    public JarFile getJarFile(Path jarPath){
        JarFile jarF = null;
        try {
            jarF = new JarFile(jarPath.toFile());
        } catch (IOException e) {
            System.err.println("Cannot find jar file : " + jarPath);
            e.printStackTrace();
        }
        assert jarF != null;
        return jarF;
    }


    public List<Class<?>> getChildClassNames(Class<?> cls, String regxContain) {
        List<Class<?>> listCCN = getSubclasses(cls);
        // rm itself
        listCCN.removeIf(s -> s.equals(cls));
        // contains, if regx not null
        if (Objects.nonNull(regxContain)) {
            listCCN.removeIf(s -> !s.getName().contains(regxContain));
        }

        System.out.println(cls.getSimpleName() + " child class = " + listCCN.size());
        System.out.println(Arrays.toString(listCCN.toArray()));
        return (listCCN);
    }

    public Map<Class<?>, List<Class<?>>> getInheritanceMap() {
        // insertion-ordered
        Map<Class<?>, List<Class<?>>> inheritMap = new LinkedHashMap();

        for (Class cls : getClasses()) {
            List<Class<?>> listClsNm = getChildClassNames(cls, null);
            Class<?> key = cls;
            listClsNm.remove(key);
            inheritMap.put(key, listClsNm);
        }

        return inheritMap;
    }

    public void writeMarkdown (String fn, String title, Map<Class<?>, List<Class<?>>> inheritMap) throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(fn)) {

            out.println("| " + title + " |");
            out.println("| ------- |");

            for (Map.Entry<Class<?>, List<Class<?>>> entry : inheritMap.entrySet()) {
                Class<?> key = entry.getKey();
                List<Class<?>> classes = entry.getValue();

                out.println("| **" + key.getName() + "** |");
                for (Class<?> cls : classes) {
                    out.println("| " + cls.getName() + " |");
                }

            }
        }
    }

    protected abstract Class[] getClasses();

    protected abstract List<Class<?>> getSubclasses(Class<?> cls);

    protected void initClassLoader(String urlSpec) {
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{ new URL(urlSpec) };
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        loader = URLClassLoader.newInstance(urls);
    }


    /**TODO multiple jars
     * Checks the given package for classes that inherited from the given class,
     * in case it's a class, or implement this class, in case it's an interface.
     *
     * @param cls     the class/interface to look for
     * @param jarFile the jar file
     * @param pkgname the package to search in
     * @return a list with all the found classnames
     */
    protected List<Class<?>> getSubclasses(Class<?> cls, JarFile jarFile, String pkgname) {
        Enumeration<JarEntry> e = jarFile.entries();

        initClassLoader("jar:file:" + jarFile + "!/");

        List<Class<?>> result = new ArrayList<>();
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);

            addClassName(className, cls, result, pkgname);
        }

//        // sort result by pkgname
//        Collections.sort(result, new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                if (s1.equals(pkgname)) {
//                    return -1;
//                }
//                if (s2.equals(pkgname)) {
//                    return 1;
//                }
//                return s1.compareTo(s2);
//            }
//        }); //, new StringCompare());
//        // remove duplicates
//        for (int i = result.size() - 1; i > 0; i--) {
//            if (result.get(i).equals(result.get(i - 1))) {
//                result.remove(i);
//            }
//        }

        return result;
    }


    /**
     * Checks the given package for classes that inherited from the given class,
     * in case it's a class, or implement this class, in case it's an interface.
     *
     * @param cls     the class/interface to look for
     * @param buildPath     the folder to contain all classes
     * @param pkgname the package to search in
     * @return a list with all the found classnames
     */
    protected List<Class<?>> getSubclasses(Class<?> cls, Path buildPath, String pkgname) {
        initClassLoader("file://" + buildPath + "/");

        List<Class<?>> result = new ArrayList<>();
//        final PathMatcher filter = FileSystems.getDefault().getPathMatcher("glob:.class");

        try (final Stream<Path> stream = Files.walk(buildPath).filter(Files::isRegularFile)) {
//            stream.forEach(System.out::println);
            String bpStr = buildPath.toString();
            stream.forEach(p -> {
//                String pkgfn = p.toString();
                // need to keep pkg in the name
                String pkgfn = p.toString().replace(bpStr, "");
                // rm / in start if has
                if (pkgfn.startsWith("/"))
                    pkgfn = pkgfn.substring(1);
                if (pkgfn.endsWith(".class")) {
                    // -6 because of .class
                    String className = pkgfn.substring(0, pkgfn.length() - 6);

                    addClassName(className, cls, result, pkgname);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void excludeSubclasses(String pkgname) {
//TODO excl classes from give pkg names
    }


    // add class if it is inherited from cls
    private void addClassName(String className, Class<?> cls, List<Class<?>> result, String pkgname) {
        // / => .
        if (className.indexOf('/') >= 0)
            className = className.replaceAll("/", ".");

        //Log.debug.println(className + " " + pkgname);
        // must match package
        if (className.startsWith(pkgname)) {
            try {
                Class clsNew = Class.forName(className, false, loader);

                // no abstract classes
                if (!Modifier.isAbstract(clsNew.getModifiers()) &&
                        // must implement interface
                        (cls.isInterface() && PackageManager.hasInterface(cls, clsNew)) ||
                        // must be derived from class
                        (!clsNew.isInterface() && PackageManager.isSubclass(cls, clsNew))) {
//                    result.add(className);
                    result.add(clsNew);
                }
            } catch (ClassNotFoundException ex) {
                Log.debug.println("Cannot find class: " + className);
                ex.printStackTrace();
            }
        }

    }



}

