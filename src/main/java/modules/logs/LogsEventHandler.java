package modules.logs;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LogsEventHandler {

    public LogsEventHandler() {

    }

    public void logsEvent(String[] messages, MessageReceivedEvent event) {
        if(messages[1].equals("get")) {
            // need error checking, like if user doesn't exist
            event.getChannel().sendMessage("logs event reached").queue();
            if(messages.length > 2) {
                event.getChannel().sendMessage("ID Input: " + messages[2]).queue();
            }
        }
    }
}
