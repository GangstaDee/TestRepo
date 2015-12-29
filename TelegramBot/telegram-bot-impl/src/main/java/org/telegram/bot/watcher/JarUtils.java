package org.telegram.bot.watcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created on 28.12.2015.
 */
public class JarUtils {

    //TO-DO wrapper for classdata and builder - from jar

    public static Map<String, ByteArrayOutputStream> loadJar(File directory, File file) {

        Map<String, ByteArrayOutputStream> classData = new HashMap<>();

        String jarPath = directory.getAbsolutePath() + "\\" + file.getName();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                    continue;
                }

                String className = jarEntry.getName().substring
                        (0, jarEntry.getName().length() - ".class".length());
                className = className.replace('/', '.');
                if(className.contains("$1"))
                    continue;
                //className = className.replace("$1", "");  //to fix

                InputStream is = jarFile.getInputStream(jarEntry);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = is.read();

                while (data != -1) {
                    buffer.write(data);
                    data = is.read();
                }
                is.close();
                classData.put(className, buffer);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return classData;
    }
}
