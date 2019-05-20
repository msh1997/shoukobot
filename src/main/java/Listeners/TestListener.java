package Listeners;

import Services.MessageParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TestListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();
    private String prefix;

    public TestListener(String prefix){
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if(!messages[0].startsWith(this.prefix)) {
            return;
        }

        messages[0] = messages[0].substring(this.prefix.length());

        if(messages[0].equals("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }

        if(messages[0].equals("echo")) {
            if(messages.length > 1) {
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 1; i < messages.length; i++) {
                    strBuilder.append(messages[i]);
                }
                String msg = strBuilder.toString();
                event.getChannel().sendMessage(msg).queue();
            }
            else {
                event.getChannel().sendMessage("Empty echo arguments.").queue();
            }
        }
    }
}
