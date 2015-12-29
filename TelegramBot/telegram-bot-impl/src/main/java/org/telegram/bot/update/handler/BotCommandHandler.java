package org.telegram.bot.update.handler;

import command.Command;
import command.CommandFactory;
import command.CommandParser;
import telegram.api.BotApi;
import telegram.domain.Update;

/**
 * Created on 29.12.2015.
 */
public class BotCommandHandler implements UpdateHandler {

    private UpdateHandler nextHandler;
    private BotApi botApi;

    @Override
    public void setNext(UpdateHandler next) {
        nextHandler = next;
    }

    public BotCommandHandler(BotApi botApi) {
        this.botApi = botApi;
    }

    public void handle(Update update) {

        if(!processCommand(update) && nextHandler != null) {
            nextHandler.handle(update);
        }
    }

    private boolean processCommand(Update update) {

        String updateMsg = update.getMessage().getText();
        if(updateMsg == null)
            return false;

        String command = CommandParser.parse(updateMsg);
        if(command == null)
            return false;

        Command action = CommandFactory.getCommand(command);
        if(action == null)
            return false;

        action.setBotApi(botApi);
        action.setChatID(update.getMessage().getChat().getId());
        action.setText(updateMsg.replaceAll(command,""));
        action.execute();
        return true;
    }
}
