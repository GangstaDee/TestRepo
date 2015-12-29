package command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 20.12.2015.
 */
public class CommandFactory {

    private static Map<String,Command> commands = new HashMap<>();

    public static Command addCommand(String text, Command command) {

        return commands.put(text,command);
    }

    public static Command getCommand(String command) {

            return commands.get(command);//here - to return clone
    }
}
