package modules.tf2.logs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogsEventHandler {

    private List<LogsUser> logsUserList = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    public LogsEventHandler() {
        try (FileReader reader = new FileReader("resources/LogsUsers.json"))
        {
            //Read JSON file
            logsUserList = objectMapper.readValue(reader, new TypeReference<List<LogsUser>>(){});

            System.out.println(logsUserList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logsEvent(String[] messages, MessageReceivedEvent event) {
        switch (messages[1]) {
            case "get":
                getTrackedUser(messages, event);
                break;
            case "add":
                addTrackedUser(messages, event);
                break;
            case "remove":
                removeTrackedUser(messages, event);
                break;
        }
    }

    private void getTrackedUser(String[] messages, MessageReceivedEvent event) {
        if(containsName(messages[2])) {
            event.getChannel().sendMessage("getTrackedUser exists reached.").queue();
            // TODO: Implement
        }
        else {
            event.getChannel().sendMessage("That user is not being tracked.").queue();
        }
    }

    private void addTrackedUser(String[] messages, MessageReceivedEvent event){

        LogsUser user = new LogsUser(messages[3], messages[2]);

        if(containsId(messages[3])) {
            event.getChannel().sendMessage("That Steam ID already exists.").queue();
        }
        else {
            logsUserList.add(user);

            try (FileWriter file = new FileWriter("resources/LogsUsers.json")) {
                objectMapper.writeValue(file, logsUserList);
                event.getChannel().sendMessage("User \"" + messages[2] + "\" with SteamID64: [" + messages[3] +  "] successfully added").queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeTrackedUser(String[] messages, MessageReceivedEvent event) {
        if(containsName(messages[2]) || containsId(messages[2])) {
            String name = "";
            String ID = "";
            for (Iterator<LogsUser> iter = logsUserList.listIterator(); iter.hasNext(); ) {
                LogsUser tempUser = iter.next();
                if (tempUser.getUsername().equals(messages[2]) || tempUser.getSteamId().equals(messages[2])) {
                    name = tempUser.getUsername();
                    ID = tempUser.getSteamId();
                    iter.remove();
                }
            }

            try (FileWriter file = new FileWriter("resources/LogsUsers.json")) {
                objectMapper.writeValue(file, logsUserList);
                event.getChannel().sendMessage("Successfully removed \"" + name + "\" with SteamId64: " + ID + " from tracked users.").queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            event.getChannel().sendMessage("User and/or steamID is not being tracked.").queue();
        }
    }

    private boolean containsName(final String name){
        return logsUserList.stream().map(LogsUser::getUsername).anyMatch(name::equals);
    }

    private boolean containsId(final String ID){
        return logsUserList.stream().map(LogsUser::getSteamId).anyMatch(ID::equals);
    }
}
