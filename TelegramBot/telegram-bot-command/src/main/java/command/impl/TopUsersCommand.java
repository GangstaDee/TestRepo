package command.impl;

import command.Command;

/**
 * Created on 27.12.2015.
 */
public class TopUsersCommand extends Command {

    @Override
    public void execute() {
        botApi.sendMessage(chatID, "HI! This is top users command!!!");
    }
}
