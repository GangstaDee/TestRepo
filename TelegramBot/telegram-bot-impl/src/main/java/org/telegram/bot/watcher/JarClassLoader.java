package org.telegram.bot.watcher;

import java.io.ByteArrayOutputStream;
import java.util.*;


/**
 * Created on 27.12.2015.
 */
public class JarClassLoader extends ClassLoader {

    private Map<String, Class> classes = new HashMap<>();
    private byte[] bytes;

    public JarClassLoader(ClassLoader parent, ByteArrayOutputStream os) {

        super(parent);
        this.bytes = os.toByteArray();

    }


//    @Override
//    protected Class findClass(final String name) throws ClassNotFoundException {
//
//        Class result = (Class) classes.get(name);
//        if (result != null) {
//            return result;
//        }
//            //return findSystemClass(name);
//        return super.findClass(name);
//    }

    @Override
    public Class loadClass(String className) throws ClassNotFoundException {

//            Class c = getParent().loadClass(name);
//            if(c != null)
//                return c;
        try {
            Class clazz = defineClass(className,
                bytes, 0, bytes.length);

            //findClass(className);
            System.out.println(clazz.getSimpleName());
            classes.put(className, clazz);

            return clazz;
        }  catch (Throwable ex) {
                ex.printStackTrace();
        }

        //  return getParent().loadClass(name);
        return null;
    }
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


