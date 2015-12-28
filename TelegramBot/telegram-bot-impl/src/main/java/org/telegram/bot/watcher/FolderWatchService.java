package org.telegram.bot.watcher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created on 27.12.2015.
 */
public class FolderWatchService implements Runnable {

    private Path pathToWatch;


    public void run() {

        File directory = new File("c:\\Users\\Dasha.Selyavko\\IdeaProjects\\" +
                "TelegramBot\\telegram-bot-command\\jars\\");
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

                            JarClassLoader classLoader = new JarClassLoader(directory,file);
                            classLoader.loadJar();


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
