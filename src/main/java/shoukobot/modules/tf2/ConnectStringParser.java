package shoukobot.modules.tf2;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectStringParser {

    public void connectString(String[] messages, MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        content = content.replaceFirst("https://", "");
        Pattern connectString = Pattern.compile("(connect) (([a-zA-Z0-9]|\\.)+)(:)*([0-9])*+(\\;\\s*(password) (.)+)*");
        Matcher m = connectString.matcher(content);
        if (m.matches()) {
            String info;
            String password = messages[messages.length - 1];
            if (messages.length == 2) {
                info = "steam://" + messages[0] + "/" + messages[1];
            } else if (messages.length == 3) {
                String host = messages[1].replace(";password", "");
                info = "steam://" + messages[0] + "/" + host + "/" + password;
            } else {
                String host = messages[1].substring(0, messages[1].length() - 1);
                info = "steam://" + messages[0] + "/" + host + "/" + password;
            }
            event.getChannel().sendMessage(info).queue();
        }
    }
}
