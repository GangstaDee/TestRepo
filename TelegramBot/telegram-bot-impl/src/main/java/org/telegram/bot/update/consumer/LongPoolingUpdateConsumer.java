package org.telegram.bot.update.consumer;

import org.telegram.bot.update.handler.BotCommandHandler;
import org.telegram.bot.update.handler.ChatMessageHandler;
import org.telegram.bot.update.handler.UpdateHandler;
import telegram.api.BotApi;
import telegram.domain.BasicResponse;
import telegram.domain.Update;

import java.util.List;

/**
 * Created on 20.12.2015.
 */
public class LongPoolingUpdateConsumer implements Runnable {

    private BotApi botApi;
    private UpdateHandler handler;
    private int offset = 0;
    private int updatesLimit = 100;
    private int timeout = 60;

    public LongPoolingUpdateConsumer(BotApi botApi) {

        this.botApi = botApi;
        this.handler = initHandlerChain();
    }

    private UpdateHandler initHandlerChain() {

        UpdateHandler chatHandler = new ChatMessageHandler(botApi);
        UpdateHandler botHandler = new BotCommandHandler(botApi);
        botHandler.setNext(chatHandler);
        return botHandler;

    }

    @Override
    public void run() {

        while(true) {

            BasicResponse<List<Update>> updates = botApi.getUpdates(offset, updatesLimit, timeout);
            List<Update> result = updates.getResult();
            if(result.isEmpty() || !updates.isOk()) {
                offset = 0;
                continue;
            }

            for(Update newUpdate : result) {
                offset = newUpdate.getUpdateId() + 1;
                handler.handle(newUpdate);
            }

        }
    }

}
