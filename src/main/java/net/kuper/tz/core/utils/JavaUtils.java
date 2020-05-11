package net.kuper.tz.core.utils;

import java.io.File;

public final class JavaUtils {


    public static void addLibraryPath(String libPath) {
        try {
            File classPath = new File(libPath);
            String path = System.getProperty("java.library.path");
            String sigarLibPath = classPath.getCanonicalPath();
            //为防止java.library.path重复加，此处判断了一下
            if (!path.contains(sigarLibPath)) {
                if (isOSWin()) {
                    path += ";" + sigarLibPath;
                    System.out.println(path);
                } else {
                    path += ":" + sigarLibPath;
                }
                System.setProperty("java.library.path", path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOSWin() {//OS 版本判断
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return true;
        } else return false;
    }
}
