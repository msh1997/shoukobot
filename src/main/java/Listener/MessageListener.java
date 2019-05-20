package Listener;

import Modules.Osu.OsuEventHandler;
import Services.MessageParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();
    private String prefix;
    private OsuEventHandler osuEventHandler;

    public MessageListener(String prefix){
        this.prefix = prefix;
        osuEventHandler = new OsuEventHandler();
    }

    public OsuEventHandler getOsuEventHandler() {
        return osuEventHandler;
    }

    public void setOsuEventHandler(OsuEventHandler osuEventHandler) {
        this.osuEventHandler = osuEventHandler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if(!messages[0].startsWith(this.prefix)) {
            return;
        }

        messages[0] = messages[0].substring(this.prefix.length());
        switch(messages[0]){
            case "osu":
                osuEventHandler.osuEvent(messages, event);
                break;
        }

        /*if(messages[0].equals("ping")) {
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
        }*/
    }
}
