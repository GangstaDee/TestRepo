package org.telegram.bot.main;

import command.Command;
import command.CommandFactory;
import command.impl.TerminateCommand;
import command.impl.TopUsersCommand;
import command.impl.YoutubeSearchCommand;
import org.telegram.bot.controller.TelegramBotController;
import org.telegram.bot.update.consumer.LongPoolingUpdateConsumer;
import org.telegram.bot.watcher.FolderWatchService;
import telegram.TelegramBot;
import telegram.api.BotApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 19.12.2015.
 */
public class Runner {

    public static void main(String[] args) {

        initCommands();
        //here - pass jar handler inside watcher
        System.out.println("Starting watcher");
        ExecutorService execService = Executors.newFixedThreadPool(1);
        execService.submit(new FolderWatchService());


        System.out.println("Starting bot");
        TelegramBot bot = new TelegramBot("##########");
        BotApi botApi = bot.create();

        TelegramBotController controller = new TelegramBotController(
                    new LongPoolingUpdateConsumer(botApi));
        controller.start();

    }

    private static void initCommands() {
        CommandFactory.addCommand("/y", new YoutubeSearchCommand());
        CommandFactory.addCommand("/you", new YoutubeSearchCommand());
        CommandFactory.addCommand("/youtube", new YoutubeSearchCommand());
        CommandFactory.addCommand("/quit", new TerminateCommand());
        //CommandFactory.addCommand("/top", new TopUsersCommand());

    }
}
