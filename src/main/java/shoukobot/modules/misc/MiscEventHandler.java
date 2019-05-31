package shoukobot.modules.misc;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import shoukobot.Main;

public class MiscEventHandler {
    public MiscEventHandler() {

    }

    public void miscEvent(String[] messages, MessageReceivedEvent event) {
        if (messages[0].equals("ping")) {
            JDA jda = Main.getJda();
            long ms = jda.getPing();
            event.getChannel().sendMessage("pong! (" + ms + "ms)").queue();
        }

        if (messages[0].equals("echo")) {
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
    }
}


