package command;

import telegram.api.BotApi;

/**
 * Created on 20.12.2015.
 */
public abstract class Command {

    public String text;
    public Integer chatID;
    public BotApi botApi;

    public void setText(String text) {

        this.text = text;
    }

    public void setChatID(Integer chatID) {

        this.chatID = chatID;
    }

    public void setBotApi(BotApi botApi) {

        this.botApi = botApi;
    }

    public abstract void execute();

//    @Override
//    protected Command clone() throws CloneNotSupportedException {
//
//        return (Command)super.clone();
//    }
}
