package shoukobot.modules.tf2.logs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static shoukobot.services.Embeds.sendEmbed;

public class LogsEventHandler {

    private List<LogsUser> logsUserList = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private LogsApiService logsApiService = new LogsApiService();

    public LogsEventHandler() {
        try (FileReader reader = new FileReader("resources/LogsUsers.json")) {
            logsUserList = objectMapper.readValue(reader, new TypeReference<List<LogsUser>>() {
            });

            System.out.println(logsUserList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listTrackedUsers(MessageReceivedEvent event) {
        String users = "";
        String ids = "";
        for (LogsUser user : logsUserList) {
            users += user.getUsername() + "\n";
            ids += user.getSteamId() + "\n";
        }

        MessageEmbed.Field field1 = new MessageEmbed.Field("Currently Tracked Users:", users, true);
        MessageEmbed.Field field2 = new MessageEmbed.Field("", ids, true);
        ArrayList<MessageEmbed.Field> fieldList = new ArrayList<>();
        fieldList.add(field1);
        fieldList.add(field2);

        sendEmbed(fieldList, new Color(0, 255, 0), event);
    }

    private void getTrackedUser(String[] messages, MessageReceivedEvent event) throws IOException, ParseException {
        if (containsName(messages[2])) {
            LogsUser user = null;
            for (LogsUser tempUser : logsUserList) {
                if (tempUser.getUsername().equals(messages[2])) {
                    user = tempUser;
                    break;
                }
            }

            int limit = 3;
            if (messages.length == 4) {
                try {
                    int num = Integer.parseInt(messages[3]);
                    if (num >= 1 && num <= 10) {
                        limit = num;
                    } else {
                        event.getChannel().sendMessage("Please enter a number from 1-10.\nDefaulting to 3.").queue();
                    }
                }catch (NumberFormatException e){
                    event.getChannel().sendMessage("Please enter a number from 1-10.\nDefaulting to 3.").queue();
                }
            }

            List<Logs> logsList = logsApiService.getLogsByUser(user, limit);

            String logString = "";
            String titleString = "";
            for (Logs logs : logsList) {
                logString += "<http://logs.tf/" + logs.getId() + ">\n";
                titleString += logs.getLogTitle() + "\n";
            }

            Field field1 = new Field("Most recent logs for " + user.getUsername() + ":", logString,true);
            Field field2 = new Field("", titleString, true);
            ArrayList<Field> fieldList = new ArrayList<>();
            fieldList.add(field1);
            fieldList.add(field2);

            sendEmbed(fieldList, new Color(0, 255, 0), event);
        } else {
            event.getChannel().sendMessage("That user is not being tracked.").queue();
        }
    }

    private void addTrackedUser(String[] messages, MessageReceivedEvent event) throws IOException, ParseException {

        if(messages.length < 4){
            event.getChannel().sendMessage("Please enter a Steam ID").queue();
            return;
        }
        LogsUser user = new LogsUser(messages[3], messages[2]);

        if (containsId(messages[3])) {
            event.getChannel().sendMessage("That Steam ID already exists.").queue();
        } else {
            if (!logsApiService.checkId(user.getSteamId())) {
                event.getChannel().sendMessage("That Steam ID is invalid.").queue();
                return;
            }

            logsUserList.add(user);

            try (FileWriter file = new FileWriter("resources/LogsUsers.json")) {
                objectMapper.writeValue(file, logsUserList);
                event.getChannel().sendMessage("User \"" + messages[2] + "\" with SteamID64: [" + messages[3] + "] successfully added").queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeTrackedUser(String[] messages, MessageReceivedEvent event) {
        if (containsName(messages[2]) || containsId(messages[2])) {
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
        } else {
            event.getChannel().sendMessage("User and/or steamID is not being tracked.").queue();
        }
    }

    public void logsEvent(String[] messages, MessageReceivedEvent event) throws IOException, ParseException {
        switch (messages[1]) {
            case "get":
                getTrackedUser(messages, event);
                break;
            case "add":
                addTrackedUser(messages, event);
                break;
            case "delete":
            case "remove":
                removeTrackedUser(messages, event);
                break;
            case "list":
                listTrackedUsers(event);
                break;
        }
    }

    private boolean containsName(final String name) {
        return logsUserList.stream().map(LogsUser::getUsername).anyMatch(name::equals);
    }

    private boolean containsId(final String ID) {
        return logsUserList.stream().map(LogsUser::getSteamId).anyMatch(ID::equals);
    }
}
