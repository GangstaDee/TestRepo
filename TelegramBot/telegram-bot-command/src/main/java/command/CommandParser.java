package command;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 20.12.2015.
 */
public class CommandParser {

    private static final Pattern commandPattern = Pattern.compile("^/[\\w]+");

    public static String parse(String messageText) {

        if(!messageText.startsWith("/"))
            return null;

        Matcher m = commandPattern.matcher(messageText);
        if(!m.find())
            return null;

        return m.group();

    }

}
