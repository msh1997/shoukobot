package shoukobot.listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.simple.parser.ParseException;
import shoukobot.modules.calc.CalculatorEventHandler;
import shoukobot.modules.customReactions.CustomEventHandler;
import shoukobot.modules.help.HelpEventHandler;
import shoukobot.modules.misc.MiscEventHandler;
import shoukobot.modules.osu.OsuEventHandler;
import shoukobot.modules.tf2.ConnectStringParser;
import shoukobot.modules.tf2.logs.LogsEventHandler;
import shoukobot.services.MessageParser;

import java.io.IOException;

public class MessageListener extends ListenerAdapter {

    private MessageParser messageParser = new MessageParser();
    private ConnectStringParser connectStringParser = new ConnectStringParser();
    private String prefix;
    private OsuEventHandler osuEventHandler;
    private CustomEventHandler customEventHandler;
    private MiscEventHandler miscEventHandler;
    private LogsEventHandler logsEventHandler;
    private HelpEventHandler helpEventHandler;
    private CalculatorEventHandler calculatorEventHandler;


    public MessageListener(String prefix) {
        this.prefix = prefix;
        osuEventHandler = new OsuEventHandler();
        customEventHandler = new CustomEventHandler();
        miscEventHandler = new MiscEventHandler();
        logsEventHandler = new LogsEventHandler();
        helpEventHandler = new HelpEventHandler();
        calculatorEventHandler = new CalculatorEventHandler();
    }

    public CalculatorEventHandler getCalculatorEventHandler () { return calculatorEventHandler; }

    public OsuEventHandler getOsuEventHandler() {
        return osuEventHandler;
    }

    public void setOsuEventHandler(OsuEventHandler osuEventHandler) { this.osuEventHandler = osuEventHandler; }

    public CustomEventHandler getCustomEventHandler() {
        return customEventHandler;
    }

    public void setCustomEventHandler(CustomEventHandler customEventHandler) { this.customEventHandler = customEventHandler; }

    public LogsEventHandler getLogsEventHandler() {
        return logsEventHandler;
    }

    public void setLogsEventHandler(LogsEventHandler logsEventHandler) {
        this.logsEventHandler = logsEventHandler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;

        System.out.println("Received a message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
        String[] messages = messageParser.parseMessage(event.getMessage().getContentRaw());

        if (customEventHandler.containsCustomEvent(event.getMessage().getContentRaw())) {
            customEventHandler.customEvent(event.getMessage().getContentRaw(), event);
        }

        if (messages.length > 0) {
            if (!messages[0].startsWith(this.prefix)) {
                if (messages[0].equals("connect")) {
                    connectStringParser.connectString(messages, event);
                }
                return;
            }

            messages[0] = messages[0].substring(this.prefix.length());
            switch (messages[0]) {
                case "osu":
                    osuEventHandler.osuEvent(messages, event);
                    break;
                case "reactions":
                    customEventHandler.customCommandEvent(messages, event.getMessage().getContentRaw(), event);
                    break;
                case "logs":
                    try {
                        logsEventHandler.logsEvent(messages, event);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "help":
                    helpEventHandler.helpEvent(messages, event);
                    break;
                case "calc":
                    calculatorEventHandler.calcEvent(messages, event);
                    break;
                default:
                    miscEventHandler.miscEvent(messages, event);
                    break;
            }
        }
    }
}
