package com.ringcentral.xmn.ta.core.utils;

import static com.ringcentral.xmn.ta.core.MobileTest.getBrand;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {

    public static String getAppName(String folderName,String extName) {
        GenericExtFilter filter = new GenericExtFilter(extName);
        File dir = new File(folderName);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists: " + folderName);
            return null;
        }

        String[] list = dir.list(filter);
        if (list.length == 0) {
            System.out.println("no files end with : " + extName);
            return null;
        }

        for (String filename : list) {
            if (filename.contains(getBrand())) {
                return filename;
            }
        }
        return null;
    }

    private static class GenericExtFilter implements FilenameFilter {

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }
        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }

    }

}
