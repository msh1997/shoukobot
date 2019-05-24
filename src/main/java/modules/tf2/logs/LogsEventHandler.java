package modules.tf2.logs;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogsEventHandler {

    List<LogsUser> logsUserList = new ArrayList<>();
    private JSONParser parser = new JSONParser();
    private ObjectMapper objectMapper = new ObjectMapper();

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
        else if(messages[1].equals("add")){
            addTrackedUser(messages, event);

        }
    }

    public void addTrackedUser(String[] messages, MessageReceivedEvent event){

        LogsUser user = new LogsUser(messages[3], messages[2]);
        logsUserList.add(user);

        try (FileWriter file = new FileWriter("resources/LogsUsers.json")) {
            objectMapper.writeValue(file, logsUserList);
            event.getChannel().sendMessage("User " + messages[2]).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
