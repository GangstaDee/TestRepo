package org.telegram.bot.watcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created on 27.12.2015.
 */
public class JarClassLoader extends ClassLoader {

    public JarClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        //byte[] classBytes = this.extraClassDefs.remove(name);
        //if (classBytes != null) {
        //    return defineClass(name, classBytes, 0, classBytes.length);
        //}
        return super.findClass(name);
    }


    public void loadJar(File directory, File file) {

        List<String> foundClasses = new ArrayList<>();

        String jarPath = directory.getAbsolutePath() + "\\" + file.getName();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration entries = jarFile.entries();

            //URL[] urls = { new URL("jar:file:" + jarPath+"!/") };
            //URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                    continue;
                }

                String className = jarEntry.getName().substring
                        (0, jarEntry.getName().length() - ".class".length());

                InputStream is = jarFile.getInputStream(jarEntry);
                //byte[]b = readData(is);

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = is.read();

                while (data != -1) {
                    buffer.write(data);
                    data = is.read();
                }
                is.close();

                byte[] classData = buffer.toByteArray();

                className = className.replace('/', '.');
                defineClass(className, classData, 0, classData.length);

                //Class cl = loadClass(className);
                //Class<?> aClass = Class.forName(className);
                Class<?> aClass2 = findClass(className);
                System.out.println(aClass2.getSimpleName());
                //loadClass(className);
                //foundClasses.add(className);
                //try {
                //Class c = cl.loadClass(className);

                // Object postman2 = (Object) c.newInstance();
                //System.out.println(c.getName());
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

            }

        } catch (IOException ex) {
            ex.printStackTrace();
            //} catch (InterruptedException e) {
            //   e.printStackTrace();
            //}
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
