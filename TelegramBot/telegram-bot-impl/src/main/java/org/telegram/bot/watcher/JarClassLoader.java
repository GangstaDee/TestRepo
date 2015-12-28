package org.telegram.bot.watcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created on 27.12.2015.
 */
public class JarClassLoader extends ClassLoader {

    private File directory;
    private File file;
    private Map<String, Class> classes = new HashMap<>();

    public JarClassLoader(File directory, File file) {
        super(JarClassLoader.class.getClassLoader());
        this.directory = directory;
        this.file = file;
    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    @Override
    protected Class findClass(final String name) throws ClassNotFoundException {

        Class result = (Class) classes.get(name);
        if (result != null) {
            return result;
        }
            //return findSystemClass(name);
        return super.findClass(name);
    }


    public void loadJar() {

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

                InputStream is = jarFile.getInputStream(jarEntry);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = is.read();

                while (data != -1) {
                    buffer.write(data);
                    data = is.read();
                }
                is.close();

                byte[] classData = buffer.toByteArray();

                className = className.replace('/', '.');
                Class clazz = defineClass(className, classData, 0, classData.length);

                Class foundClass = findClass(className);
                System.out.println(foundClass.getSimpleName());

                classes.put(className,clazz);

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//
//        if (name.equals("command.Command")) {
//                try {
//                    InputStream is = JarClassLoader.class.getClassLoader().getResourceAsStream("command/Command.class");
//                    byte[] buf = new byte[10000];
//                    int len = is.read(buf);
//                    return defineClass(name, buf, 0, len);
//                } catch (IOException e) {
//                    throw new ClassNotFoundException("", e);
//                }
//            }
//            return getParent().loadClass(name);


//
//        if(!"reflection.MyObject".equals(name))
//            return super.loadClass(name);
//
//        try {
//            String url = "file:C:/data/projects/tutorials/web/WEB-INF/" +
//                    "classes/reflection/MyObject.class";
//            URL myUrl = new URL(url);
//            URLConnection connection = myUrl.openConnection();
//            InputStream input = connection.getInputStream();
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//            int data = input.read();
//
//            while(data != -1){
//                buffer.write(data);
//                data = input.read();
//            }
//
//            input.close();

//            byte[] classData = buffer.toByteArray();
//
//            return defineClass("reflection.MyObject",
//                    classData, 0, classData.length);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    }

    public static byte[] readData(InputStream inputStream) {
        try {
            ByteArrayOutputStream boTemp = null;
            byte[] buffer = null;
            try {
                int read;
                buffer = new byte[8192];
                boTemp = new ByteArrayOutputStream();
                while ((read=inputStream.read(buffer, 0, 8192)) > -1) {
                    boTemp.write(buffer, 0, read);
                }
                return boTemp.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
