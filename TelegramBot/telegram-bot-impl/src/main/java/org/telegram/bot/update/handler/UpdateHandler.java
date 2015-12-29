package org.telegram.bot.update.handler;

import telegram.domain.Update;

/**
 * Created on 20.12.2015.
 */
public interface UpdateHandler {

    void handle(Update update);

    void setNext(UpdateHandler next);
}
