package audit;

import beast.core.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * @author Walter Xie
 */
public abstract class AbstractClassLoader {

    //TODO
    protected String MY_PATH = System.getProperty("user.home") + "/WorkSpace/";

    protected URLClassLoader loader;

    /**
     * @return a Map of super class in key, and the Set of its sub classes in value.
     */
    public Map<Class<?>, Set<Class<?>>> getInheritanceMap() {
        // insertion-ordered
        Map<Class<?>, Set<Class<?>>> inheritMap = new LinkedHashMap();

        Class[] superClasses = getSuperClasses();
        assert superClasses.length > 1;
        for (Class cls : superClasses) {
            Set<Class<?>> listClsNm = getSubClassesFrom(cls, null);
            Class<?> key = cls;
            listClsNm.remove(key);
            inheritMap.put(key, listClsNm);
        }

        return inheritMap;
    }

    public void writeSummary(PrintWriter out) { // TODO
//        out.println("# ");
        out.println("## " + getTitle());

        String incl = "";
//        for (Class exclCls : getExclClasses()) {
//            if (exclCls.isAssignableFrom(cls))
//                return true;
//        }
        out.println("Include " + incl);

        String excl = "";
        out.println("Exclude " + excl);
    }


    @Deprecated
    public void writeMarkdown (String fn, String title, Map<Class<?>, Set<Class<?>>> inheritMap)
            throws FileNotFoundException {

        try (PrintWriter out = new PrintWriter(fn)) {

            out.println("| " + title + " |");
            out.println("| ------- |");

            for (Map.Entry<Class<?>, Set<Class<?>>> entry : inheritMap.entrySet()) {
                Class<?> key = entry.getKey();
                Set<Class<?>> classes = entry.getValue();

                out.println("| **" + key.getName() + "** |");
                for (Class<?> cls : classes) {
                    out.println("| " + cls.getName() + " |");
                }

            }
        }
    }

    // the substring to start with
    protected abstract String[] getPkgNames();

    // define super classes to load (together with their subclasses)
    // such as beast.core.CalculationNode.class
    protected abstract Class[] getSuperClasses();

    // either jar or dir
    protected abstract String getPathString();

    // define the parent class/interface to exclude itself
    // and its child classes from the analysis
    protected abstract Class[] getExclClasses();

    /**
     * The string starting with any of given substrings will be excluded.
     * This can be used to exclude either a package  beast.app,
     * or some classes starting with beast.evolution.tree.Tree*, such as TreeStatLogger, TreeDistribution.
     */
    protected abstract String[] getExclStartWith();

    // for Markdown
    public abstract String getTitle();

    //****** init ******//

    protected JarFile getJarFile(Path jarPath){
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

    protected void initClassLoader(String urlSpec) {
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{ new URL(urlSpec) };
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        loader = URLClassLoader.newInstance(urls);
    }

    //****** load and filter classes ******//

    /**
     * method to get subclasses from a super
     */
    private Set<Class<?>> getSubClassesFrom(Class<?> superClass) {
        String p = getPathString();
        if (p.endsWith(".jar")) {
            Path jarPath = Paths.get(p);
            assert Files.isRegularFile(jarPath);
            JarFile jarF =  getJarFile(jarPath);
            return getSubClassesIn(superClass, jarF);
        } else {
            Path dir = Paths.get(p);
            assert Files.isDirectory(dir);
            return getSubClassesIn(superClass, dir);
        }
    }
    // return PackageManager.find(cls, pkgDir); // load all installed pkgs

    // not include super class itself
    private Set<Class<?>> getSubClassesFrom(Class<?> superClass, String regxContain) {
        Set<Class<?>> cnnSet = getSubClassesFrom(superClass);
        // rm itself
        cnnSet.removeIf(s -> s.equals(superClass));
        // contains, if regx not null
        if (Objects.nonNull(regxContain)) {
            cnnSet.removeIf(s -> !s.getName().contains(regxContain));
        }

        System.out.println(superClass.getSimpleName() + " child class = " + cnnSet.size());
        System.out.println(Arrays.toString(cnnSet.toArray()));
        return cnnSet;
    }

    /**TODO multiple jars
     * Checks the given package for classes that inherited from the given class,
     * in case it's a class, or implement this class, in case it's an interface.
     *
     * @param superClass     the class/interface to look for
     * @param jarFile the jar file
     * @return a list with all the found classnames
     */
    private Set<Class<?>> getSubClassesIn(Class<?> superClass, JarFile jarFile) {
        Enumeration<JarEntry> e = jarFile.entries();

        initClassLoader("jar:file:" + jarFile + "!/");

        Set<Class<?>> resultSet = new LinkedHashSet<>();
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);

            addClassName(className, superClass, resultSet);
        }

//        // sort resultSet by startWith
//        Collections.sort(resultSet, new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                if (s1.equals(startWith)) {
//                    return -1;
//                }
//                if (s2.equals(startWith)) {
//                    return 1;
//                }
//                return s1.compareTo(s2);
//            }
//        }); //, new StringCompare());
//        // remove duplicates
//        for (int i = resultSet.size() - 1; i > 0; i--) {
//            if (resultSet.get(i).equals(resultSet.get(i - 1))) {
//                resultSet.remove(i);
//            }
//        }

        return resultSet;
    }


    /**
     * Checks the given package for classes that inherited from the given class,
     * in case it's a class, or implement this class, in case it's an interface.
     *
     * @param superClass     the class/interface to look for
     * @param buildPath     the folder to contain all classes
     * @return a list with all the found classnames
     */
    private Set<Class<?>> getSubClassesIn(Class<?> superClass, Path buildPath) {
        initClassLoader("file://" + buildPath + "/");

        Set<Class<?>> resultSet = new LinkedHashSet<>();
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

                    addClassName(className, superClass, resultSet);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // if this is either the same as, or is a superclass or superinterface, of given cls
    private boolean isExcluded(Class<?> cls) {
        for (Class exclCls : getExclClasses()) {
            if (exclCls.isAssignableFrom(cls))
                return true;
        }
        for (String exclPkg : getExclStartWith()) {
            if (cls.getName().startsWith(exclPkg))
                return true;
        }
        return false;
    }


    // add class, if it startWith, and if it is inherited from superClass
    // no interface, abstract class, inner class
    private void addClassName(String className, Class<?> superClass, Set<Class<?>> result) {
        // / => .
        if (className.indexOf('/') >= 0)
            className = className.replaceAll("/", ".");

        //Log.debug.println(className + " " + pkgname);
        // must match package
        if (startWithPkgName(className)) {
            try {
                Class clsNew = Class.forName(className, false, loader);

                // no interface, abstract class, inner class
                if (!Modifier.isAbstract(clsNew.getModifiers()) && !clsNew.isMemberClass() &&
                        !Modifier.isInterface(clsNew.getModifiers()) &&
                        superClass.isAssignableFrom(clsNew) && !isExcluded(clsNew) ) {

                    result.add(clsNew);
                }
            } catch (ClassNotFoundException ex) {
                Log.debug.println("Cannot find class: " + className);
                ex.printStackTrace();
            }
        }

    }

//     * @param startWith the start substring to locate the selected package to load
    private boolean startWithPkgName(String className) {
        for (String pkg : getPkgNames()) {
            if (className.startsWith(pkg)) return true;
        }
        // if nothing in getPkgNames(), then return true
        return getPkgNames().length == 0;
    }

}

