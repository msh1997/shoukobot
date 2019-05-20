package listener;

import modules.customReactions.CustomEventHandler;
import modules.misc.MiscEventHandler;
import modules.osu.OsuEventHandler;
import services.MessageParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();
    private String prefix;
    private OsuEventHandler osuEventHandler;
    private CustomEventHandler customEventHandler;
    private MiscEventHandler miscEventHandler;

    public MessageListener(String prefix){
        this.prefix = prefix;
        osuEventHandler = new OsuEventHandler();
        customEventHandler = new CustomEventHandler();
        miscEventHandler = new MiscEventHandler();
    }

    public OsuEventHandler getOsuEventHandler() {
        return osuEventHandler;
    }

    public void setOsuEventHandler(OsuEventHandler osuEventHandler) {
        this.osuEventHandler = osuEventHandler;
    }

    public CustomEventHandler getCustomEventHandler() { return customEventHandler; }

    public void setCustomEventHandler(CustomEventHandler customEventHandler) { this.customEventHandler = customEventHandler; }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if(event.getAuthor().isBot()) return;

        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if(customEventHandler.containsCustomEvent(event.getMessage().getContentRaw())){
            customEventHandler.customEvent(event.getMessage().getContentRaw(), event);
        }

        if(!messages[0].startsWith(this.prefix)) return;

        messages[0] = messages[0].substring(this.prefix.length());
        switch(messages[0]){
            case "osu":
                osuEventHandler.osuEvent(messages, event);
                break;
            case "reactions":
                customEventHandler.customCommandEvent(messages, event.getMessage().getContentRaw(), event);
                break;
            default:
                miscEventHandler.miscEvent(messages, event);
                break;
        }
    }
}
