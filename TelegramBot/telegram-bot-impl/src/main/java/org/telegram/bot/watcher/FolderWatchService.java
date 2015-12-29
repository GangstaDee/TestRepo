package org.telegram.bot.watcher;

import command.Command;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created on 27.12.2015.
 */
public class FolderWatchService implements Runnable {

    private Path pathToWatch;


    public void run() {

        File directory = new File("#########");
        pathToWatch = directory.toPath();

        try (WatchService watcher = pathToWatch.getFileSystem().newWatchService()) {

            pathToWatch.register(watcher, ENTRY_MODIFY);

            while (true) {

                WatchKey key = watcher.take();
                WatchEvent.Kind kind;

                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    kind = watchEvent.kind();

                    if (ENTRY_MODIFY.equals(kind)) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();

                        File file = newPath.toFile();
                        if (file.getName().endsWith(".jar")) {

                            //move to separate handler
                            Map<String, ByteArrayOutputStream> classData = JarUtils.loadJar(directory,file);

                            ClassLoader parent = JarClassLoader.class.getClassLoader();
                            for(String className : classData.keySet()) {
                                JarClassLoader classLoader = new JarClassLoader(parent,classData.get(className));
                                Class loaded = classLoader.loadClass(className);
                                if(loaded == null) {
                                    System.out.println("Failed to load class " + className);
                                    continue;
                                }
                                final boolean assignableFrom = loaded.isAssignableFrom(Command.class);
                                //after - put into commandfactory which extend command
                                System.out.println("loaded " + loaded.getName());
                            }



                        }
                    }
                }

                //call onFolderUpdate
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
