package command.impl;

import command.Command;

/**
 * Created on 20.12.2015.
 */
public class TerminateCommand  extends Command {

    @Override
    public void execute() {
        botApi.sendMessage(chatID, "Bye :(");
        System.exit(0);
    }
}
