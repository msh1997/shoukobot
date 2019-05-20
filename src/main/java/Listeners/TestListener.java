package Listeners;

import Services.MessageParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TestListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if(!messages[0].equals(".r")){
            return;
        }

        if(messages[1].equals("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }
    }
}
