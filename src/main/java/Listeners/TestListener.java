package Listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TestListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        if(event.getMessage().getContentRaw().equals("!ping"))
            event.getChannel().sendMessage("Pong!");
    }
}
