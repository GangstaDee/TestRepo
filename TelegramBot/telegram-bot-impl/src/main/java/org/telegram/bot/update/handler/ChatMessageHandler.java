package org.telegram.bot.update.handler;

import telegram.api.BotApi;
import telegram.domain.Update;

/**
 * Created on 29.12.2015.
 */
public class ChatMessageHandler implements UpdateHandler {

    private UpdateHandler nextHandler;
    private BotApi botApi;

    @Override
    public void setNext(UpdateHandler next) {
        nextHandler = next;
    }

    public ChatMessageHandler(BotApi botApi) {

        this.botApi = botApi;
    }

    @Override
    public void handle(Update update) {

        //push events to history


        if(nextHandler != null)
            nextHandler.handle(update);
    }
}
