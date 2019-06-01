package shoukobot.modules.misc;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import shoukobot.Main;

public class MiscEventHandler {

    public MiscEventHandler() {

    }

    public void ping(MessageReceivedEvent event) {
        long ms = Main.getPing();
        event.getChannel().sendMessage(":ping_pong: pong! `[" + ms + " ms]`").queue();
    }

    public void echo(String[] messages, MessageReceivedEvent event) {
        if (messages.length > 1) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 1; i < messages.length - 1; i++) {
                strBuilder.append(messages[i] + " ");
            }
            strBuilder.append(messages[messages.length - 1]);
            String msg = strBuilder.toString();
            event.getChannel().sendMessage(msg).queue();
            event.getMessage().delete().queue();
        } else {
            event.getChannel().sendMessage("Empty echo arguments.").queue();
        }
    }

    public void miscEvent(String[] messages, MessageReceivedEvent event) {
        switch (messages[0]) {
            case "ping":
                ping(event);
                break;
            case "echo":
                echo(messages, event);
                break;
        }
    }
}


