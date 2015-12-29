package org.telegram.bot.controller;


/**
 * Created on 20.12.2015.
 */
public class TelegramBotController {

    private Runnable consumer;

    public TelegramBotController(Runnable consumer) {

        this.consumer = consumer;
    }

    public void start() {
        consumer.run();
    }
}


