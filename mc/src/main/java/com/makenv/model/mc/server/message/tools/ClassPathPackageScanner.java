package com.makenv.model.mc.server.message.tools;
/**
 * Created by wgy on 2016/12/27.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * This scanner is used to find out all classes in a package.
 *
 */
public class ClassPathPackageScanner implements PackageScanner {
    private Logger logger = LoggerFactory.getLogger(ClassPathPackageScanner.class);

    private String basePackage;
    private ClassLoader cl;
    private Class annotationFilter;
    //private List<? extends Annotation> annotationFilter;
    /**
     * Construct an instance and specify the base package it should scan.
     * @param basePackage The base package to scan.
     */
    public ClassPathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();

    }

    /**
     * Construct an instance with base package and class loader.
     * @param basePackage The base package to scan.
     * @param cl Use this class load to locate the package.
     */
    public ClassPathPackageScanner(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }

    public ClassPathPackageScanner(String basePackage, ClassLoader cl,Class annotation) {
        this.basePackage = basePackage;
        this.cl = cl;
        this.annotationFilter = annotation;
    }

    public ClassPathPackageScanner(String basePackage,Class annotation) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();
        this.annotationFilter = annotation;
    }

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     * @throws IOException
     */

    public List<String> getFullyQualifiedClassNameList() throws IOException {
        logger.info("开始扫描包{}下的所有类", basePackage);

        return doScan(basePackage, new ArrayList<>());
    }

    /**
     * Actually perform the scanning procedure.
     *
     * @param basePackage
     * @param nameList A list to contain the result.
     * @return A list of fully qualified names.
     *
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        // replace dots with splashes
        String splashPath = StringUtil.dotToSplash(basePackage);

        // get file path
        URL url = cl.getResource(splashPath);
        String filePath = StringUtil.getRootPath(url);

        // Get classes in that package.
        // If the web server unzips the jar file, then the classes will exist in the form of
        // normal file in the directory.
        // If the web server does not unzip the jar file, then classes will exist in jar file.
        List<String> names = null; // contains the name of the class file. e.g., Apple.class will be stored as "Apple"
        if (isJarFile(filePath)) {
            // jar file
            if (logger.isInfoEnabled()) {
                logger.info("{} 是一个JAR包", filePath);
            }

            names = readFromJarFile(filePath, splashPath);

            for (String name : names) {
                if (isClassFile(name)) {
                    try {
                        String fullName = StringUtil.SplashToDot(name).replaceAll(".class","");
                        Class clazz = Class.forName(fullName);
                        if(annotationFilter != null) {
                            Object annocationClass = clazz.getDeclaredAnnotation(annotationFilter);
                            if(annocationClass != null) {
                                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                                nameList.add(fullName);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        logger.info("class is not defined");
                    }

                } else {
                    // this is a directory
                    // check this directory for more classes
                    // do recursive invocation
                    doScan(basePackage + "." + name, nameList);
                }
            }
        } else {
            // directory
            if (logger.isInfoEnabled()) {
                logger.info("{} 是一个目录", filePath);
            }
            names = readFromDirectory(filePath);
            for (String name : names) {
                if (isClassFile(name)) {
                    try {
                        String fullName = toFullyQualifiedName(name, basePackage);
                        Class clazz = Class.forName(fullName);
                        if(annotationFilter != null) {
                            Object annocationClass = clazz.getDeclaredAnnotation(annotationFilter);
                            if(annocationClass != null) {
                                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                                nameList.add(fullName);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        logger.info("class is not defined");
                    }

                } else {
                    // this is a directory
                    // check this directory for more classes
                    // do recursive invocation
                    doScan(basePackage + "." + name, nameList);
                }
            }
        }

        if (logger.isInfoEnabled()) {
            for (String n : nameList) {
                logger.info("找到{}", n);
            }
        }

        return nameList;
    }

    /**
     * Convert short class name to fully qualified name.
     * e.g., String -> java.lang.String
     */
    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtil.trimExtension(shortName));

        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("从JAR包中读取类: {}", jarPath);
        }

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    /**
     * For test purpose.
     */
    public static void main(String[] args) throws Exception {
        ClassPathPackageScanner scan = new ClassPathPackageScanner("com.makenv.ep.web");
        scan.getFullyQualifiedClassNameList();
    }

}