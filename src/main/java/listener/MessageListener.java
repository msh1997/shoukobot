package listener;

import java.util.regex.*;
import modules.customReactions.CustomEventHandler;
import modules.logs.LogsEventHandler;
import modules.misc.MiscEventHandler;
import modules.osu.OsuEventHandler;
import services.MessageParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Date;

public class MessageListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();
    private String prefix;
    private OsuEventHandler osuEventHandler;
    private CustomEventHandler customEventHandler;
    private MiscEventHandler miscEventHandler;
    private LogsEventHandler logsEventHandler;

    public MessageListener(String prefix){
        this.prefix = prefix;
        osuEventHandler = new OsuEventHandler();
        customEventHandler = new CustomEventHandler();
        miscEventHandler = new MiscEventHandler();
        logsEventHandler = new LogsEventHandler();
    }

    public OsuEventHandler getOsuEventHandler() {
        return osuEventHandler;
    }

    public void setOsuEventHandler(OsuEventHandler osuEventHandler) {
        this.osuEventHandler = osuEventHandler;
    }

    public CustomEventHandler getCustomEventHandler() { return customEventHandler; }

    public void setCustomEventHandler(CustomEventHandler customEventHandler) { this.customEventHandler = customEventHandler; }

    public LogsEventHandler getLogsEventHandler() { return logsEventHandler; }

    public void setLogsEventHandler(LogsEventHandler logsEventHandler) {this.logsEventHandler = logsEventHandler;}

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if(event.getAuthor().isBot()) return;

        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if(customEventHandler.containsCustomEvent(event.getMessage().getContentRaw())){
            customEventHandler.customEvent(event.getMessage().getContentRaw(), event);
        }

        if(!messages[0].startsWith(this.prefix)) {
            if (messages[0].equals("connect")) {
                String content = event.getMessage().getContentRaw();
                content = content.replaceFirst("https://", "");
                Pattern connectString = Pattern.compile("(connect) (([a-zA-Z0-9]|\\.)+)\\:[0-9]+(\\;\\s*(password) (.)+)*");
                Matcher m = connectString.matcher(content);
                if (m.matches()) {
                    String info;
                    String password = messages[messages.length-1];
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
            return;
        }

        messages[0] = messages[0].substring(this.prefix.length());
        switch(messages[0]){
            case "osu":
                osuEventHandler.osuEvent(messages, event);
                break;
            case "reactions":
                customEventHandler.customCommandEvent(messages, event.getMessage().getContentRaw(), event);
                break;
            case "logs":
                logsEventHandler.logsEvent(messages, event);
                break;
            default:
                miscEventHandler.miscEvent(messages, event);
                break;
        }
    }
}
